package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.chat.ChatScreen
import ui.details.DetailsScreen
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
        composable(AppNavigation.Details.route) { navBackStackEntry ->
            val id = AppNavigation.Details.getId(navBackStackEntry)
            id?.let {
                DetailsScreen(superheroId = id)
            }
        }
        composable(AppNavigation.Chat.route) { navBackStackEntry ->
            val args = AppNavigation.Chat.getChatArgs(navBackStackEntry)
            args?.let {
                ChatScreen(chatArguments = args)
            }
        }

    }
}

sealed class AppNavigation(
    val route: String
) {
    data object Home : AppNavigation("Home")

    data object Details : AppNavigation("Details/{id}") {
        fun createRouteWithId(id: String): String {
            return "Details/$id"
        }

        fun getId(backStackEntry: NavBackStackEntry): String? =
            backStackEntry.arguments?.getString("id")
    }

    data object Chat : AppNavigation("Chat/{id}/{name}/{image}") {
        data class ChatArguments(val id: String, val name: String, val img: String)

        fun createRouteWithArgs(args: ChatArguments): String {
            args.apply {
                return "Chat/$id/$name/$img"
            }
        }

        fun getChatArgs(backStackEntry: NavBackStackEntry): ChatArguments? {
            backStackEntry.arguments?.apply {
                return ChatArguments(
                    id = getString("id") ?: "",
                    name = getString("name") ?: "",
                    img = getString("img") ?: ""
                )
            }
            return null
        }
    }
}

