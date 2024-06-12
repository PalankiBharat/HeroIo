import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import ui.navigation.AppNavHost
import ui.navigation.LocalNavigationProvider
import ui.theme.AppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App() {
    AppTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }
        CompositionLocalProvider(value = LocalNavigationProvider provides rememberNavController()) {
            AppNavHost()
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()
