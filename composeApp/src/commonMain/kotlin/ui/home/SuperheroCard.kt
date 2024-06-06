package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.hero.domain.model.Superhero
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import io.ktor.http.Url

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
                        dominantColorState.color,
                        Color.Black
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
            text = bio,
            color = Color.Black,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
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

