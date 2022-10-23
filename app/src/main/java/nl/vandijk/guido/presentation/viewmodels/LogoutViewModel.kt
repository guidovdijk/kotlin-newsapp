package nl.vandijk.guido.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.models.UiMessageModel
import nl.vandijk.guido.util.SessionManager

class LogoutViewModel : ViewModel() {
    private val message = MutableStateFlow<UiMessageModel?>(null)
    var logoutMessageState: StateFlow<UiMessageModel?> = message

    fun logout(navController: NavController) {
        try {
            SessionManager.removeData()
            navController.navigate("home")
        } catch (e: Exception) {
            message.tryEmit(
                UiMessageModel(
                    MessageId = R.string.ui_toast_logout_error,
                    Success = false
                )
            )
        }
    }
}