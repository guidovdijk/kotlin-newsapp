package nl.vandijk.guido.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.vandijk.guido.presentation.screens.*

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                loginClick = {
                    navController.navigate("login")
                },
                pageNavigation = {
                    navController.navigate("$it")
                },
                onDetailClick = {
                    navController.navigate("detail/$it")
                }
            )
        }
        composable(
            "liked"
        ) {
            LikedScreen(
                navController = navController,
                loginClick = {
                    navController.navigate("login")
                },
                pageNavigation = {
                    navController.navigate("$it")
                },
                onDetailClick = {
                    navController.navigate("detail/$it")
                }
            )
        }
        composable(
            "detail/{detailId}",
            listOf(
                navArgument("detailId") { type = NavType.IntType }
            )
        ) {
            NewsDetailPage(navController, it.arguments?.getInt("detailId") ?: -1)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
    }
}