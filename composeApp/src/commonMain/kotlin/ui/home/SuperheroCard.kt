package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.hero_bard_bg
import io.ktor.http.Url
import org.jetbrains.compose.resources.painterResource
import ui.theme.brighten

@Composable
fun SuperheroCard(modifier: Modifier = Modifier, superhero: Superhero) {
    Card(
        modifier = modifier.fillMaxWidth().padding(10.dp),
        shape = AbsoluteRoundedCornerShape(16.dp),
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        val imageUrl = superhero.imagesEntity?.smallImage
        val networkLoader = rememberNetworkLoader()
        val dominantColorState = rememberDominantColorState(loader = networkLoader)
        LaunchedEffect(imageUrl) {
            imageUrl?.let {
                dominantColorState.updateFrom(Url(imageUrl))
            }
        }

        Row(
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        dominantColorState.color.copy(alpha = 0.5f),
                        dominantColorState.color,
                    )
                )
            ),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight().background(Color.Black),
                model = superhero.imagesEntity?.midImage,
                contentScale = ContentScale.Crop,
                contentDescription = superhero.name
            )
            println(superhero.imagesEntity?.midImage)
            SuperheroCardDetails(superhero = superhero)
        }

    }

}

@Composable
fun SuperheroCardDetails(superhero: Superhero) {
    Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 12.dp, top = 10.dp)) {
        Text(
            text = superhero.name,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = superhero.fullName.orEmpty(),
            color = Color.Black,
        )
        var bio = ""
        superhero.apply {
            bio =
                "Known by many names such as ${aliases?.joinToString(",")} born in $placeOfBirth. This superhero first appeared on $firstAppearance"
        }
        Text(
            text = bio, color = Color.Black, maxLines = 3, overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.fillMaxHeight())
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "More Info", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "More Info"
            )
        }
    }
}

@Composable
fun SuperheroCardNew(modifier: Modifier = Modifier, superhero: Superhero) {
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
            modifier = Modifier
                .padding(start = 32.dp)
                .align(Alignment.CenterVertically)
                .drawWithContent {
                    drawContent()
                    val pathData = listOf(
                        194.9f to 4f,
                        89.8f to 4f,
                        4f to 98.9f,
                        94f to 195f,
                        143f to 196f,
                        179f to 197f,
                        258f to 197f,
                        344f to 98.9f,
                        261.5f to 4f,
                        228.8f to 4f,
                        179.1f to 15.3f,
                        105.1f to 15.3f,
                        27.7f to 98.9f,
                        102.3f to 181.9f,
                        250.3f to 184.7f,
                        329.9f to 98.9f,
                        255.3f to 17.4f,
                        194.9f to 15.3f
                    )
                    val maxX = pathData.maxOf { it.first }
                    val maxY = pathData.maxOf { it.second }

                    // Helper function to convert absolute coordinates to percentages
                    fun toX(x: Float) = (x / maxX) * size.width
                    fun toY(y: Float) = (y / maxY) * size.height

                    val path = Path().apply {
                        // Define path movements // These movements are defined in absolute coordinates and taken from the SVG
                        val moves = listOf(
                            listOf(
                                194.9f to 4f,
                                89.8f to 4f,
                                4f to 98.9f,
                                94f to 195f,
                                143f to 196f
                            ),
                            listOf(
                                179f to 197f,
                                258f to 197f,
                                344f to 98.9f,
                                261.5f to 4f,
                                228.8f to 4f
                            ),
                            listOf(
                                179.1f to 15.3f,
                                105.1f to 15.3f,
                                27.7f to 98.9f,
                                102.3f to 181.9f,
                                250.3f to 184.7f,
                                329.9f to 98.9f,
                                255.3f to 17.4f,
                                194.9f to 15.3f
                            )
                        )
                        // Move to start point
                        moveTo(toX(194.9f), toY(4f))

                        // Iterate through moves to create the path
                        moves.forEach { segment ->
                            segment.forEachIndexed { index, (x, y) ->
                                if (index == 0) moveTo(toX(x), toY(y)) else lineTo(toX(x), toY(y))
                            }
                        }
                    }
                    for (i in 0..100) {
                        val percent = i.toFloat() / 100f
                        drawPath(
                            path = path,
                            color = dominantColorState.color.copy(alpha = 0.1f * percent), // Semi-transparent black color for glow
                            style = Stroke(width = 8.dp.toPx() * (1 - percent)),
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
                text = superhero.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            var bio = ""
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
            105.1f to 15.3f,
            27.7f to 98.9f,
            102.3f to 181.9f,
            250.3f to 184.7f,
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
            lineTo(toX(105.1f), toY(15.3f))
            lineTo(toX(27.7f), toY(98.9f))
            lineTo(toX(102.3f), toY(181.9f))
            lineTo(toX(250.3f), toY(184.7f))
            lineTo(toX(329.9f), toY(98.9f))
            lineTo(toX(255.3f), toY(17.4f))
            lineTo(toX(194.9f), toY(15.3f))
            close()
        }
        return Outline.Generic(
            path = path
        )
    }
}



