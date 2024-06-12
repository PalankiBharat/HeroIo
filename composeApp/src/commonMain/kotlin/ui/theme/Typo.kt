package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.outfit_bold
import heroio.composeapp.generated.resources.outfit_medium
import heroio.composeapp.generated.resources.outfit_regular
import heroio.composeapp.generated.resources.outfit_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun MontserratFont(): FontFamily {
    return FontFamily(
        Font(Res.font.outfit_bold, FontWeight.Bold),
        Font(Res.font.outfit_medium, FontWeight.Medium),
        Font(Res.font.outfit_regular, FontWeight.Normal),
        Font(Res.font.outfit_semibold, FontWeight.SemiBold),
    )
}


@Composable
fun Typography(): Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = MontserratFont(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        ),
        bodyMedium = TextStyle(
            fontFamily = MontserratFont(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        ),
        bodySmall = TextStyle(
            fontFamily = MontserratFont(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        ),
        titleLarge = TextStyle(
            fontFamily = MontserratFont(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        ),
        titleMedium = TextStyle(
            fontFamily = MontserratFont(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        titleSmall = TextStyle(
            fontFamily = MontserratFont(),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        ),
    )
}
