package nl.vandijk.guido.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.vandijk.guido.R
import nl.vandijk.guido.business.Account
import nl.vandijk.guido.presentation.models.UiMessageModel
import nl.vandijk.guido.util.SessionManager

class LoginViewModel : ViewModel() {
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

    suspend fun login(navController: NavController) {
        if (username.value.isEmpty() || password.value.isEmpty()) {
            message.tryEmit(
                UiMessageModel(
                    MessageId = R.string.api_error_login_400,
                    Success = false
                )
            )
            return
        }

        try {
            val response = account.login(username.value, password.value)

            response.onSuccess { res ->
                saveData(username.value, password.value, res.AuthToken)
                navController.navigate("home")
            }.onFailure { res ->
                message.tryEmit(
                    UiMessageModel(
                        MessageId = R.string.api_error_login_401,
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

    private fun saveData(email: String, pwd: String, token: String) {
        SessionManager.saveData(email, pwd, token)
    }
}
