package nl.vandijk.guido.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.vandijk.guido.R
import nl.vandijk.guido.business.Account
import nl.vandijk.guido.presentation.models.UiMessageModel

class RegisterViewModel : ViewModel() {
    private val account: Account = Account()

    private val username = MutableStateFlow("")
    var usernameState: StateFlow<String> = username

    private val password = MutableStateFlow("")
    var passwordState: StateFlow<String> = password

    private val message = MutableStateFlow<UiMessageModel?>(null)
    var messageState: StateFlow<UiMessageModel?> = message

    fun updateUsername(value: String) {
        username.tryEmit(value)
    }

    fun updatePassword(value: String) {
        password.tryEmit(value)
    }

    suspend fun register() {
        if (usernameState.value.isEmpty() || passwordState.value.isEmpty()) {
            message.tryEmit(
                UiMessageModel(
                    MessageId = R.string.api_error_login_400,
                    Success = false
                )
            )
            return
        }

        /*
        test123g
        test123
         */
        try {
            val response = account.register(username.value, password.value)

            response.onSuccess { res ->
                if (res.Success) {
                    message.tryEmit(
                        UiMessageModel(
                            MessageId = R.string.api_success_register,
                            Success = true
                        )
                    )
                } else {
                    message.tryEmit(
                        UiMessageModel(
                            MessageId = R.string.api_error_register,
                            Success = false
                        )
                    )
                }
            }.onFailure { res ->
                message.tryEmit(
                    UiMessageModel(
                        MessageId = R.string.api_error_general,
                        Success = false
                    )
                )
            }
        } catch (e: Exception) {
            message.tryEmit(
                UiMessageModel(
                    MessageId = R.string.api_error_no_connection,
                    Success = false
                )
            )
        }
    }
}