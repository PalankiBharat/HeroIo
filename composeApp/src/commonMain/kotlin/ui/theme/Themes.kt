package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = themeColor,
    onPrimary = Color.White,
    primaryContainer = appBG,
    onPrimaryContainer = Color.White,
    onSecondaryContainer = Color.White,
    secondaryContainer = appBG,
    background = appBG,
    surface = appBG,
    onBackground = Color.White,
    onSurface = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onSurfaceVariant = Color.White,
    onTertiaryContainer = Color.White,
    secondary = themeColor,
)


@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(colorScheme = DarkColorScheme, typography = Typography(), content = content)
}

