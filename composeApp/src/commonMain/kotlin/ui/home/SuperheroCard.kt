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
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathParser
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
                    val path = PathParser()
                        .parsePathString("M 194.9 4 H 89.8 L 4 98.9 L 94 195 L 143 196 M 179 197 H 258 L 344 98.9 L 261.5 4 H 228.8 M 179.1 15.3 H 105.1 L 27.7 98.9 L 102.3 181.9 L 250.3 184.7 L 329.9 98.9 L 255.3 17.4 L 194.9 15.3")
                        .toPath()
                    // Composable size do not effect the path size so we need to scale it
                    val matrix = Matrix()
                    matrix.scale(
                        0.8f, 0.8f
                    )
                    path.transform(matrix)
                    drawPath(
                        path = path,
                        style = Stroke(width = 4f),
                        color = Color.White
                    )
                }
                .clip(MyCustomShape())
                .padding(start = 6.dp)
                .fillMaxWidth(0.24f)
                .fillMaxHeight(0.42f),
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
        val path = PathParser()
            .parsePathString("M 195 16 M 179.084 15.296 H 105.096 L 27.72 98.884 L 102.272 181.908 L 250.248 184.732 L 329.88 98.884 L 255.328 17.372 L 194.896 15.296")
            .toPath()
        val matrix = Matrix()
        matrix.scale(
            0.8f, 0.8f
        )
        path.transform(matrix)
        return Outline.Generic(
            path = path
        )
    }
}


