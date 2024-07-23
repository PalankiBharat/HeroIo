package ui.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun StatsBar(
    modifier: Modifier = Modifier,
    color: Color,
    progress: Float,
    cornerRadius: Dp = 10.dp,
    image: DrawableResource
) {
    // Create an Animatable instance for the progress value
    val animatedProgress = remember { Animatable(0f) }

    // Launch a coroutine to animate the progress
    LaunchedEffect(progress) {
            animatedProgress.animateTo(
                targetValue = progress,
                animationSpec = tween(durationMillis = 1600)
            )
    }


    Row(
        modifier = modifier.padding(horizontal = 6.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Stats",
            modifier = Modifier.padding(end = 6.dp).size(24.dp),
            contentScale = ContentScale.Fit
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.2.dp,
                    shape = RoundedCornerShape(size = cornerRadius),
                    color = color
                )
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress.value)
                    .height(20.dp)
                    .padding(6.dp)
            ) {
                val size = size
                drawRoundRect(
                    size = size,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            color,
                            color
                        )
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
                )
            }
        }
    }

}