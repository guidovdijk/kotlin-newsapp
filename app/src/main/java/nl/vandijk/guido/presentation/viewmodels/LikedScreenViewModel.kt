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
import java.net.UnknownHostException


class LikedScreenViewModel : ViewModel() {
    private val articleDetail: ArticleDetail = ArticleDetail()

    private val mutableListArticle = MutableStateFlow<Result<List<NewsCardModel>>?>(null)
    var articleListState: StateFlow<Result<List<NewsCardModel>>?> = mutableListArticle

//    private val mutableError = MutableStateFlow<Exception?>(null)
//    var errorState: MutableStateFlow<Exception?> = mutableError

    private val mutableError = MutableStateFlow<UiMessageModel?>(null)
    var errorState: MutableStateFlow<UiMessageModel?> = mutableError

    init {
        viewModelScope.launch() {
            getLikedArticles()
        }
    }

    fun refresh() {
        viewModelScope.launch() {
            mutableListArticle.emit(null)

            getLikedArticles()
        }
    }

    suspend fun getLikedArticles() {
        try {
            val authToken = SessionManager.getAuthToken()
            val articles = articleDetail.getLikedArticles(authToken)

            mutableListArticle.emit(articles)
            mutableError.emit(null)
        } catch (e: Exception) {
            when (e) {
                is IOException,
                is UnknownHostException -> {
                    mutableError.emit(
                        UiMessageModel(
                            MessageId = R.string.api_error_no_connection,
                            Success = false
                        )
                    )
                }
                else -> {
                    mutableError.emit(
                        UiMessageModel(
                            MessageId = R.string.api_error_general,
                            Success = false
                        )
                    )
                }
            }
            mutableListArticle.emit(null)
        }
    }
}