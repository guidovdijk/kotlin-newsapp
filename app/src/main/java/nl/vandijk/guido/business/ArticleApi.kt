package nl.vandijk.guido.business

import nl.vandijk.guido.data.ArticleResultEntity
import retrofit2.Response
import retrofit2.http.*

interface ArticleApi {
    // Multiple Articles
    @GET("api/Articles")
    suspend fun getArticles(
        @Query("Id") id: Int?,
        @Header("x-authtoken") authToken: String
    ): Response<ArticleResultEntity>

    @GET("api/Articles")
    suspend fun getArticles(@Query("Id") id: Int?): Response<ArticleResultEntity>

    // Specific Article
    @GET("api/Articles/{Id}")
    suspend fun getArticleDetail(
        @Path("Id") Id: Int,
        @Header("x-authtoken") authToken: String
    ): Response<ArticleResultEntity>

    @GET("api/Articles/{Id}")
    suspend fun getArticleDetail(@Path("Id") Id: Int): Response<ArticleResultEntity>

    // Liked Article list
    @GET("api/Articles/liked")
    suspend fun getLikedArticles(@Header("x-authtoken") authToken: String): Response<ArticleResultEntity>

    // Like article
    @PUT("api/Articles/{id}/like")
    suspend fun addLikedArticle(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String
    ): Response<Unit>

    // Removed Liked Article
    @DELETE("api/Articles/{id}/like")
    suspend fun removeLikedArticle(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String
    ): Response<Unit>
}