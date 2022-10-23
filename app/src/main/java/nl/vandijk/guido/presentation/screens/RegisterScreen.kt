package nl.vandijk.guido.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.components.Message
import nl.vandijk.guido.presentation.components.TopBar
import nl.vandijk.guido.presentation.viewmodels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    val username by viewModel.usernameState.collectAsState()
    val password by viewModel.passwordState.collectAsState()
    val message by viewModel.messageState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                isLoggedIn = false,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, stringResource(R.string.ui_icon_back))
                    }
                },
            )
        },
        content = {
            Column(modifier = Modifier
                .padding(it)
                .padding(8.dp)) {
                Text(
                    text = stringResource(R.string.ui_register_screen_login_text),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(48.dp))

                TextField(
                    value = username,
                    onValueChange = { it -> viewModel.updateUsername(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = @Composable { Text(stringResource(R.string.ui_input_label_email)) },
                    placeholder = @Composable { Text(stringResource(R.string.ui_input_placeholder_email)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { it -> viewModel.updatePassword(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = @Composable { Text(stringResource(R.string.ui_input_label_password)) },
                    placeholder = @Composable { Text(stringResource(R.string.ui_input_placeholder_password)) },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { coroutineScope.launch { viewModel.register() } }
                ) {
                    Text(stringResource(R.string.ui_register_screen_login_text))
                }

                message?.let { m -> Message(uiMessageModel = m) }
            }
        }
    )
}