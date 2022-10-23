package nl.vandijk.guido.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.vandijk.guido.R
import nl.vandijk.guido.business.ArticleDetail
import nl.vandijk.guido.presentation.models.NewsCardModel
import nl.vandijk.guido.presentation.models.UiMessageModel
import nl.vandijk.guido.util.SessionManager
import java.io.IOException

class NewsDetailViewModel : ViewModel() {
    private val articleDetail: ArticleDetail = ArticleDetail()

    private val mutableArticle = MutableStateFlow<Result<NewsCardModel>?>(null)
    var articleState: StateFlow<Result<NewsCardModel>?> = mutableArticle

    private val mutableLike = MutableStateFlow(false)
    var likeState: StateFlow<Boolean> = mutableLike

    private val mutableError = MutableStateFlow<UiMessageModel?>(null)
    var errorState: StateFlow<UiMessageModel?> = mutableError

    suspend fun fetch(id: Int) {
        try {
            val article = articleDetail.getArticle(id, SessionManager.getAuthToken())

            mutableArticle.emit(article)
            updateInitialLikeValue()
            mutableError.emit(null)
        } catch (e: IOException) {
            mutableError.emit(UiMessageModel(R.string.api_error_no_connection, false))
        } catch (e: Exception) {
            mutableError.emit(UiMessageModel(R.string.api_error_general, false))
        }
    }

    suspend fun updateInitialLikeValue() {
        mutableArticle.value?.onSuccess { data ->
            mutableLike.emit(data.IsLiked)
        }?.onFailure {
            mutableLike.emit(false)
        }
    }

    fun refresh(detailId: Int) {
        mutableArticle.tryEmit(null)

        viewModelScope.launch {
            fetch(detailId)
        }
    }

    fun updateArticleLikeState(id: Int, initialIsLiked: Boolean) {
        /*
        Liked state needs to be update to a new value.
        If a liked article gets clicked (isLiked == True) it needs to be removed.
        And if an article that is not yet liked gets clicked (isLiked == false) it needs to be added.

        So we swap the Boolean value in newLikedState where:
        - True == adding it to the liked list
        - False == removing it from the liked list.
        */
        val isNewLikedArticle: Boolean = !initialIsLiked

        viewModelScope.launch {
            val authToken = SessionManager.getAuthToken()
            if (isNewLikedArticle) {
                addArticleToLikeList(id, authToken)
            } else {
                removeArticleFromLikeList(id, authToken)
            }
        }
    }

    private suspend fun addArticleToLikeList(id: Int, authToken: String) {
        try {
            articleDetail.addLikeArticle(id, authToken).onSuccess {
                mutableLike.emit(true)
                mutableError.emit(null)
            }.onFailure {
                mutableLike.emit(false) // If failed return the initial value of false
                throw it
            }
        } catch (e: IOException) {
            mutableError.emit(UiMessageModel(R.string.api_error_no_connection, false))
        } catch (e: Exception) {
            mutableError.emit(UiMessageModel(R.string.api_error_article_liked, false))
        }
    }

    private suspend fun removeArticleFromLikeList(id: Int, authToken: String) {
        try {
            articleDetail.removeLikeArticle(id, authToken).onSuccess {
                mutableLike.emit(false)
                mutableError.emit(null)
            }.onFailure {
                mutableLike.emit(true) // If failed return the initial value of true
                throw it
            }
        } catch (e: IOException) {
            mutableError.emit(UiMessageModel(R.string.api_error_no_connection, false))
        } catch (e: Exception) {
            mutableError.emit(UiMessageModel(R.string.api_error_article_unliked, false))
        }
    }
}