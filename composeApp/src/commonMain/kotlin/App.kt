
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.home.HomeScreen

@Composable
@Preview
fun App() {
    initKoin()
    MaterialTheme {
       HomeScreen()
    }
}