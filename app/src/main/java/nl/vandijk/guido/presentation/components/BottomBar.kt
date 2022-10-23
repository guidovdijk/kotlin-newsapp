package nl.vandijk.guido.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.vandijk.guido.R

@Composable
fun BottomBar(navController: NavController, isLoggedIn: Boolean, pageNavigation: (String) -> Unit){
    if(isLoggedIn){
        NavigationBar(
//            containerColor = colorResource(id = R.color.purple_700),
//            contentColor = colorResource(id = R.color.white)
        ) {
            NavigationBarItem(
                modifier = Modifier
                    .weight(1f),
                selected = navController.currentDestination?.route == "home",
                onClick = { pageNavigation("home") },
                icon = { Icon(Icons.Filled.Home, stringResource(R.string.ui_icon_home)) },
                label = { Text(stringResource(R.string.ui_navigation_home)) }
            )
            NavigationBarItem(
                modifier = Modifier
                    .weight(1f),
                selected = navController.currentDestination?.route == "liked",
                onClick = { pageNavigation("liked") },
                icon = { Icon(Icons.Filled.Favorite, stringResource(R.string.ui_icon_liked)) },
                label = { Text(stringResource(R.string.ui_navigation_liked)) }
            )
        }
    }
}