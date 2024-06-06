package ui.home

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero

@Composable
fun SuperheroCard(modifier: Modifier = Modifier, superhero: Superhero) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AbsoluteRoundedCornerShape(10.dp),
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(),
                model = superhero.imagesEntity?.midImage,
                contentDescription = superhero.name
            )
            println(superhero.imagesEntity?.midImage)
            SuperheroCardDetails(superhero = superhero)
        }

    }

}

@Composable
fun SuperheroCardDetails(superhero: Superhero) {
    Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 12.dp)) {
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
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "More Info")
        }
    }
}

