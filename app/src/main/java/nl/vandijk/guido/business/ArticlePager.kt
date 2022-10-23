package nl.vandijk.guido.business

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.vandijk.guido.data.ArticleMapper
import nl.vandijk.guido.presentation.models.ArticleResultModel
import nl.vandijk.guido.presentation.models.NewsCardModel
import nl.vandijk.guido.util.SessionManager

class ArticlePager : PagingSource<Int, NewsCardModel>() {
    private val articleMapper = ArticleMapper()
    private var nextId: Int? = null
    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, NewsCardModel>): Int? {
        return null
    }

    private suspend fun fetch(startKey: Int, loadSize: Int): Result<ArticleResultModel> {
        val authToken = SessionManager.getAuthToken()
        val response = if (authToken.isNotEmpty()) {
            RetroFitInstance.articleApi.getArticles(nextId, authToken)
        } else {
            RetroFitInstance.articleApi.getArticles(nextId)
        }

        return when {
            response.isSuccessful -> {
                val body = response.body()

                if (body !== null) {
                    articleMapper.mapListPager(body)
                } else {
                    Result.failure(IllegalStateException())
                }
            }
            else -> Result.failure(Exception(IllegalStateException()))
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsCardModel> {
        try {
            val result = fetch(params.key ?: -1, 20).getOrElse {
                return LoadResult.Error(it)
            }

            nextId = result.NextId

            return LoadResult.Page(result.Results, null, nextId)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}