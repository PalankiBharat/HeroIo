package ui.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import createPathFromString
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.hero_bard_bg
import io.ktor.http.Url
import org.jetbrains.compose.resources.painterResource
import ui.theme.brighten
import ui.utils.Constants.CARD_GLOW_PATH
import ui.utils.Constants.CARD_SHAPE_PATH


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SuperheroCardNew(
    modifier: Modifier = Modifier,
    superhero: Superhero,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val painter = painterResource(resource = Res.drawable.hero_bard_bg)
    val imageUrl = superhero.imagesEntity?.smallImage
    val networkLoader = rememberNetworkLoader()
    val dominantColorState = rememberDominantColorState(loader = networkLoader)
    LaunchedEffect(imageUrl) {
        imageUrl?.let {
            dominantColorState.updateFrom(Url(imageUrl))
        }
    }
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 10.dp)
            .drawBehind {
                with(painter)
                {
                    draw(
                        size = size,
                        colorFilter = ColorFilter.tint(dominantColorState.color.brighten(0.4f))
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier.Companion
                .sharedElement(
                    sharedTransitionScope.rememberSharedContentState(key = "HeroImage-${superhero.id}"),
                    animatedVisibilityScope = animatedContentScope
                )
                .padding(start = 32.dp)
                .align(Alignment.CenterVertically)
                .drawWithContent {
                    drawContent()
                    val path = createPathFromString(CARD_SHAPE_PATH, size.width, size.height)
                    val glowPath = createPathFromString(CARD_GLOW_PATH, size.width, size.height)
                    for (i in 0..100) {
                        val percent = i.toFloat() / 100f
                        drawPath(
                            path = glowPath,
                            color = dominantColorState.color.brighten(0.2f)
                                .copy(alpha = (1f - percent) / 25f), // Semi-transparent color for glow
                            style = Stroke(width = 9.dp.toPx() * (percent)),
                        )
                    }
                    drawPath(
                        path = path,
                        color = Color.White,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
                .clip(MyCustomShape())
                .padding(start = 6.dp)
                .fillMaxWidth(0.27f)
                .fillMaxHeight(0.5f),
            model = superhero.imagesEntity?.midImage,
            contentScale = ContentScale.FillBounds,
            contentDescription = superhero.name
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxHeight(0.9f).padding(end = 22.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.Companion
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "Text-${superhero.name}"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                text = superhero.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            var bio: String
            superhero.apply {
                bio =
                    "Known by many names such as ${aliases?.joinToString(",")} born in $placeOfBirth. This superhero first appeared on $firstAppearance"
            }
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = bio,
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )

        }

    }
}

class MyCustomShape() : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData = listOf(
            179.1f to 15.3f,
            100.1f to 15.3f,
            27.7f to 98.9f,
            102.3f to 170.9f,
            245.3f to 171.7f,
            329.9f to 98.9f,
            255.3f to 17.4f,
            194.9f to 15.3f
        )

        val maxX = pathData.maxOf { it.first }
        val maxY = pathData.maxOf { it.second }

        val width = size.width
        val height = size.height

        // Helper function to convert absolute coordinates to percentages
        fun toX(x: Float) = (x / maxX) * width
        fun toY(y: Float) = (y / maxY) * height

        val path = Path().apply {
            moveTo(toX(179.1f), toY(15.3f))
            lineTo(toX(100.1f), toY(15.3f))
            lineTo(toX(25.7f), toY(90f))
            lineTo(toX(100.3f), toY(160.9f))
            lineTo(toX(245.3f), toY(160.7f))
            lineTo(toX(310.9f), toY(88.9f))
            lineTo(toX(250.3f), toY(15.4f))
            lineTo(toX(194.9f), toY(15.3f))
            close()
        }
        return Outline.Generic(
            path = path
        )
    }
}


