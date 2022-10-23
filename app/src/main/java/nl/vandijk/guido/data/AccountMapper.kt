package nl.vandijk.guido.data

import nl.vandijk.guido.presentation.models.AuthResponse
import nl.vandijk.guido.presentation.models.RegisterResponse


class AccountMapper {
    fun mapRegister(entity: RegisterResponseEntity): Result<RegisterResponse> = runCatching {
        with(entity) {
            RegisterResponse(
                Message = Message!!,
                Success = Success!!,
            )
        }
    }

    fun mapLogin(entity: AuthResponse): Result<AuthResponse> = runCatching {
        with(entity) {
            AuthResponse(
                AuthToken = AuthToken!!
            )
        }
    }
}