package nl.vandijk.guido.presentation.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.components.Message
import nl.vandijk.guido.presentation.components.TopBar
import nl.vandijk.guido.presentation.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
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
        content = { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)) {
                Text(
                    text = stringResource(R.string.ui_login_screen_login_text),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier.clickable { navController.navigate("register") },
                    text = stringResource(R.string.ui_login_screen_register_text),
                    style = MaterialTheme.typography.headlineSmall,
                    textDecoration = TextDecoration.Underline
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
                    onClick = {
                        coroutineScope.launch {
                            viewModel.login(navController)
                        }
                    }
                ) {
                    Text(stringResource(R.string.ui_login_screen_login_text))
                }

                Spacer(modifier = Modifier.height(8.dp))

                message?.let { m -> Message(uiMessageModel = m) }
            }
        }
    )
}