package nl.vandijk.guido.business

import nl.vandijk.guido.data.AccountMapper
import nl.vandijk.guido.presentation.models.AccountCredentials
import nl.vandijk.guido.presentation.models.AuthResponse
import nl.vandijk.guido.presentation.models.RegisterResponse


class Account {
    private val accountMapper = AccountMapper()

    suspend fun login(username: String, password: String): Result<AuthResponse> {
        val credentials = AccountCredentials(username, password)
        val response = RetroFitInstance.accountApi.login(credentials)

        return when {
            response.isSuccessful -> {
                val body = response.body()

                if (body !== null) {
                    accountMapper.mapLogin(body)
                } else {
                    Result.failure(IllegalStateException())
                }
            }
            else -> Result.failure(IllegalStateException())
        }
    }

    suspend fun register(username: String, password: String): Result<RegisterResponse> {
        val credentials = AccountCredentials(username, password)
        val response = RetroFitInstance.accountApi.register(credentials)

        return when {
            response.isSuccessful -> {
                val body = response.body()

                if (body !== null) {
                    accountMapper.mapRegister(body)
                } else {
                    Result.failure(IllegalStateException())
                }
            }
            else -> Result.failure(IllegalStateException())
        }
    }
}