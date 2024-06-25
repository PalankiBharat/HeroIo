package ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CharacteristicLabel(modifier: Modifier = Modifier, value: String, image: Painter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .aspectRatio(1f)
            .drawBehind {
                drawUpperHalfHexagon()
            }
            .drawBehind {
                drawHexagon()
            }
        ) {
            Image(
                painter = image,
                contentDescription = "asd",
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .align(Alignment.Center)
            )
        }
        Text(text = value, modifier = Modifier.padding(top = 10.dp))
    }

}

fun DrawScope.drawHexagon() {
    val path = Path()
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = size.width / 2.2 // Adjust the radius as needed

    for (i in 0..5) {
        val angle = (60 * i).toDouble().toRadians()
        val x = (centerX + radius * cos(angle)).toFloat()
        val y = (centerY + radius * sin(angle)).toFloat()
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    withTransform(
        transformBlock = {
            rotate(90f)
        }
    ) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 3.dp.toPx())
        )
    }

}

fun DrawScope.drawUpperHalfHexagon() {
    val path = Path()
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = size.width / 2 // Adjust the radius as needed


    // Calculate the six vertices of the hexagon
    val points = List(6) { i ->
        val angle = (60 * i).toDouble().toRadians()
        val x = (centerX + radius * cos(angle)).toFloat()
        val y = (centerY + radius * sin(angle)).toFloat()
        x to y
    }

    // Draw the upper half of the hexagon, excluding two sides
    path.moveTo(points[0].first, points[0].second) // Top point
    path.lineTo(points[1].first, points[1].second) // Top-right point
    path.lineTo(points[2].first, points[2].second) // Bottom-right point
    path.moveTo(points[0].first, points[0].second) // Top point
    path.lineTo(points[5].first, points[5].second) // Top-left point
    path.lineTo(points[4].first, points[4].second) // Top-left point


    withTransform(
        transformBlock = {
            rotate(270f)
        }
    ) {
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(colors = listOf(Color.Transparent, Color.Cyan)),
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

fun Double.toRadians(): Double = this * PI / 180.0