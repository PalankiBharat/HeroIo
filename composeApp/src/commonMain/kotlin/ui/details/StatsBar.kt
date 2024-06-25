package ui.details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StatsBar(
    modifier: Modifier = Modifier,
    color: Color,
    backgroundColor: Color,
    progress: Float,
    cornerRadius: Dp
) {
    Box(
        modifier = modifier
            .border(
                width = 1.2.dp,
                shape = RoundedCornerShape(size = cornerRadius),
                color = color
            )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(20.dp)
                .padding(6.dp)
        ) {
            val size = size
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        color,
                        color
                    )
                ),
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
            )
            val brush = Brush.verticalGradient(
                colors = listOf(
                    backgroundColor,
                    color,
                )
            )
            drawCircle(
                brush = brush,
                center = Offset(x = size.width - size.height / 2, y = size.height / 2),
                radius = size.height / 1.2f
            )
        }
    }
}