package nl.vandijk.guido.business

import nl.vandijk.guido.data.RegisterResponseEntity
import nl.vandijk.guido.presentation.models.AccountCredentials
import nl.vandijk.guido.presentation.models.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AccountApi {
    @Headers("Content-Type: application/json")
    @POST("api/Users/register")
    suspend fun register(@Body body: AccountCredentials): Response<RegisterResponseEntity>

    @Headers("Content-Type: application/json")
    @POST("api/Users/login")
    suspend fun login(@Body body: AccountCredentials): Response<AuthResponse>
}