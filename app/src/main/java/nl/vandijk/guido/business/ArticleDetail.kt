package nl.vandijk.guido.business

import nl.vandijk.guido.data.ArticleMapper
import nl.vandijk.guido.presentation.models.NewsCardModel

class ArticleDetail {
    private val articleMapper = ArticleMapper()

    suspend fun getArticle(id: Int, authToken: String): Result<NewsCardModel> {
        val response = if (authToken.isNotEmpty()) {
            RetroFitInstance.articleApi.getArticleDetail(id, authToken)
        } else {
            RetroFitInstance.articleApi.getArticleDetail(id)
        }

        return when {
            response.isSuccessful -> {
                val body = response.body()

                if (body !== null && body.Results[0] !== null) {
                    articleMapper.map(body.Results[0])
                } else {
                    Result.failure(Exception())
                }
            }
            else -> Result.failure(Exception())
        }
    }

    suspend fun getLikedArticles(authToken: String): Result<List<NewsCardModel>> {
        val response = RetroFitInstance.articleApi.getLikedArticles(authToken)

        return when {
            response.isSuccessful -> {
                val body = response.body()

                if (body !== null) {
                    articleMapper.mapList(body.Results)
                } else {
                    Result.failure(Exception())
                }
            }
            else -> Result.failure(Exception())
        }
    }

    suspend fun addLikeArticle(id: Int, authToken: String): Result<String> {
        val response = RetroFitInstance.articleApi.addLikedArticle(id, authToken)

        return when {
            response.isSuccessful -> {
                Result.success("")
            }
            else -> Result.failure(IllegalStateException())
        }
    }

    suspend fun removeLikeArticle(id: Int, authToken: String): Result<String> {
        val response = RetroFitInstance.articleApi.removeLikedArticle(id, authToken)

        return when {
            response.isSuccessful -> {
                Result.success("")
            }
            else -> Result.failure(IllegalStateException())
        }
    }
}