package ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ui.chat.ChatScreen
import ui.details.DetailsScreen
import ui.home.HomeScreen

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavHost")
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost() {
    val navHostController = LocalNavigationProvider.current
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = AppNavigation.Home,
        ) {
            composable<AppNavigation.Home> {
                HomeScreen(    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable)
            }
            composable<Details> { navBackStackEntry ->
                val data: Details = navBackStackEntry.toRoute()
                DetailsScreen(
                    superheroId = data.id,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
            composable<AppNavigation.Chat> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<AppNavigation.Chat>()
                ChatScreen(chatArguments = args)
            }

        }
    }

}

@Serializable
sealed class AppNavigation {

    @Serializable
    data object Home


    @Serializable
    data class Chat(
        val id: String, val name: String, val img: String
    )

}


@Serializable
data class Details(val id: String)

