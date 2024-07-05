package ui.chat

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.weight_scale
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeroChatResponse(modifier: Modifier = Modifier, text: String) {
    var enabled by remember { mutableStateOf(false) }
    val animatable by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f, label = "",
        animationSpec = tween(durationMillis = 500, delayMillis = 600)
    )
    val scale by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f, label = "",
        animationSpec = tween(durationMillis = 350, delayMillis = 300)
    )
    LaunchedEffect(key1 = Unit) {
        enabled = true
    }
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)
        .graphicsLayer(
            scaleX = scale, scaleY = scale,
            transformOrigin = TransformOrigin(0f, 0f)
        )
        .drawBehind {
            val cornerRadius = 6.dp.toPx()
            translate(left = 16f * animatable, top = 16f * animatable) {
                drawRoundRect(
                    color = Color.Black,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Fill
                )
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            drawRoundRect(
                color = Color.Black,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Fill
            )
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Stroke(width = 1.dp.toPx())
            )

        }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Image(
                painter = painterResource(Res.drawable.weight_scale),
                contentDescription = "",
                modifier = Modifier
                    .padding(14.dp)
                    .size(20.dp)
            )
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                val path = Path().apply {
                    moveTo(size.width / 2, 0f)
                    lineTo(size.width / 2, size.height)
                }

                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f), 50f)
                    )
                )
            }
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodySmall
            )

        }
    }
}

@Composable
fun UserChatResponse(modifier: Modifier = Modifier, text: String) {
    var enabled by remember { mutableStateOf(false) }
    val animatable by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f, label = "",
        animationSpec = tween(durationMillis = 500, delayMillis = 600)
    )
    LaunchedEffect(key1 = Unit) {
        enabled = true
    }
    val scale by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f, label = "",
        animationSpec = tween(durationMillis = 350, delayMillis = 300)
    )
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)
        .graphicsLayer(
            scaleX = scale, scaleY = scale,
            transformOrigin = TransformOrigin(0f, 0f)
        )
        .drawBehind {
            val cornerRadius = 6.dp.toPx()
            translate(left = -16f * animatable, top = 16f * animatable) {
                drawRoundRect(
                    color = Color.Black,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Fill
                )
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            drawRoundRect(
                color = Color.Black,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Fill
            )
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                style = Stroke(width = 1.dp.toPx())
            )

        }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodySmall
            )

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                val path = Path().apply {
                    moveTo(size.width / 2, 0f)
                    lineTo(size.width / 2, size.height)
                }

                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f), 50f)
                    )
                )
            }

            Image(
                painter = painterResource(Res.drawable.weight_scale),
                contentDescription = "",
                modifier = Modifier
                    .padding(14.dp)
                    .size(20.dp)
            )

        }
    }
}