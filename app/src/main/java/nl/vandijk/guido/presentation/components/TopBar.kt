package nl.vandijk.guido.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.viewmodels.LogoutViewModel
import nl.vandijk.guido.ui.theme.Purple700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController? = null,
    isLoggedIn:Boolean,
    navigationIcon: @Composable (() -> Unit)? = null,
    loginClick: (() -> Unit?)? = null,
    refreshClick: (() -> Unit?)? = null,
    viewModel:LogoutViewModel = viewModel()
){
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorResource(id = R.color.purple_700),
            navigationIconContentColor = colorResource(id = R.color.white),
            titleContentColor = colorResource(id = R.color.white),
            actionIconContentColor = colorResource(id = R.color.white)
        ),
        title = {
            Text(stringResource(id = R.string.ui_topbar_title))
        },
        navigationIcon = {
            if (navigationIcon != null) {
                navigationIcon()
            }
        },
//            contentColor = (colorResource(id = R.color.white)),
//            backgroundColor = (),
        actions = {
            if(isLoggedIn){
                if(navController !== null){
                    Button(onClick = { viewModel.logout(navController) }) {
                        Text(stringResource(id = R.string.ui_topbar_logout))
                    }
                }
            } else {
                if(loginClick !== null){
                    IconButton(onClick = { loginClick() }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(R.string.ui_icon_menu),
                        )
                    }
                }
            }
            if(refreshClick !== null) {
                IconButton(onClick = { refreshClick() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.ui_icon_refresh),
                    )
                }
            }
        }
    )
}