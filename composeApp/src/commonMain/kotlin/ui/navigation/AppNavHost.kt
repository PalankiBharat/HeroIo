package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.home.HomeScreen

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavHost")
}

@Composable
fun AppNavHost() {
    val navHostController = LocalNavigationProvider.current
    NavHost(
        navController = navHostController,
        startDestination = AppNavigation.Home.route,
    ) {
        composable(AppNavigation.Home.route) {
            HomeScreen()
        }
        composable(AppNavigation.Details.route) {

        }
    }
}

sealed class AppNavigation(
    val route: String
) {
    data object Home : AppNavigation("Home")
    data object Details : AppNavigation("Details/{id}"){
        fun createRouteWithId(id: String): String {
            return "Details/$id"
        }
        fun getId(backStackEntry: NavBackStackEntry): String? =
            backStackEntry.arguments?.getString("id")
    }
}

