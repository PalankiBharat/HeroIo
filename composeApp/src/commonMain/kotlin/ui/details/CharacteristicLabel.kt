package ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.alignment_evil
import heroio.composeapp.generated.resources.alignment_good
import heroio.composeapp.generated.resources.alignment_neutral
import heroio.composeapp.generated.resources.gender_female
import heroio.composeapp.generated.resources.gender_male
import heroio.composeapp.generated.resources.gender_neutral
import heroio.composeapp.generated.resources.publisher_dc
import heroio.composeapp.generated.resources.publisher_marvel
import heroio.composeapp.generated.resources.publisher_other
import heroio.composeapp.generated.resources.publisher_sony
import heroio.composeapp.generated.resources.publisher_startrek
import heroio.composeapp.generated.resources.publisher_universal
import heroio.composeapp.generated.resources.race_inhuman
import heroio.composeapp.generated.resources.race_xmen
import org.jetbrains.compose.resources.DrawableResource
import ui.theme.themeColor
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CharacteristicLabel(
    modifier: Modifier = Modifier,
    value: String,
    image: Painter,
    color: Color = themeColor
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
            .drawBehind {
                drawUpperHalfHexagon()
            }
            .drawBehind {
                drawHexagon(color = color)
            }
        ) {
            Image(
                painter = image,
                contentDescription = value,
                modifier = Modifier
                    .fillMaxSize(0.45f)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = value.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
            modifier = Modifier.padding(top = 4.dp).fillMaxWidth(0.7f),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            maxLines = 1
        )
    }

}

fun DrawScope.drawHexagon(color: Color) {
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
            color = color,
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


fun getRaceImage(race: String): DrawableResource {
    val mutants = setOf("Mutant", "Mutant / Clone")
    val humans = setOf(
        "Human",
        "Human / Radiation",
        "Human / Cosmic",
        "Human / Altered",
        "Human / Clone",
        "Human-Kree",
        "Human-Vuldarian",
        "Human-Vulcan",
        "Human-Spartoi"
    )
    val aliens = setOf(
        "Icthyo Sapien",
        "Ungaran",
        "Cosmic Entity",
        "Xenomorph XX121",
        "Android",
        "Symbiote",
        "Atlantean",
        "Alien",
        "Neyaphem",
        "New God",
        "Alpha",
        "Bizarro",
        "Inhuman",
        "Metahuman",
        "Kryptonian",
        "Kakarantharaian",
        "Black Racer",
        "Zen-Whoberian",
        "Strontian",
        "Kaiju",
        "Saiyan",
        "Gorilla",
        "Rodian",
        "Flora Colossus",
        "Gungan",
        "Bolovaxian",
        "Czarnian",
        "Martian",
        "Spartoi",
        "Luphomoid",
        "Parademon",
        "Yautja",
        "Clone",
        "Talokite",
        "Korugaran",
        "Zombie",
        "Tamaranean",
        "Frost Giant",
        "Yoda's species"
    )
    val mythicalCreatures = setOf("Vampire", "Demon", "Maiar", "Amazon")
    val inhumans = setOf("Inhuman")
    val gods = setOf("God / Eternal", "New God", "Asgardian", "Demi-God", "Eternal")

    return when (race) {
        in mutants -> Res.drawable.race_xmen
        in humans -> Res.drawable.race_xmen
        in aliens -> Res.drawable.race_xmen
        in mythicalCreatures -> Res.drawable.race_xmen
        in inhumans -> Res.drawable.race_xmen
        in gods -> Res.drawable.race_xmen
        else -> Res.drawable.race_inhuman
    }
}

fun getPublisherImage(publisher: String): DrawableResource {
    return when (publisher) {
        in listOf(
            "Marvel Comics", "Giant-Man", "Toxin", "Angel", "Goliath", "Spoiler", "Icon Comics",
            "Deadpool", "Evil Deadpool", "Jean Grey", "Phoenix", "Ms Marvel II", "Rune King Thor",
            "Anti-Venom", "Scorpion", "Vindicator II", "Anti-Vision", "Thunderbird II", "Ant-Man",
            "Venom III", "Spider-Carnage", "Angel Salvadore"
        ) -> Res.drawable.publisher_marvel

        in listOf(
            "DC Comics",
            "Oracle",
            "Nightwing",
            "Wildstorm",
            "Batman II",
            "Batgirl",
            "Batgirl III",
            "Batgirl V",
            "Robin II",
            "Robin III",
            "Red Hood",
            "Red Robin",
            "Superman Prime One-Million",
            "Black Racer",
            "Speed Demon",
            "Flash IV"
        ) -> Res.drawable.publisher_dc

        "Star Trek" -> Res.drawable.publisher_startrek

        "Sony Pictures" -> Res.drawable.publisher_sony

        "Universal Studios" -> Res.drawable.publisher_universal

        in listOf(
            "Dark Horse Comics", "NBC - Heroes", "SyFy", "Archangel", "Tempest", "Gemini V",
            "South Park", "Binary", "ABC Studios", "George Lucas", "Meltdown", "Titan Books",
            "Power Woman", "Rebellion", "Iron Lad", "Power Man", "Image Comics", "Microsoft",
            "Boom-Boom", "J. K. Rowling", "J. R. R. Tolkien", "Aztar"
        ) -> Res.drawable.publisher_other

        else -> Res.drawable.publisher_other
    }
}

fun getGenderImage(gender: String): DrawableResource {
    return when (gender) {
        "Male" -> Res.drawable.gender_male
        "Female" -> Res.drawable.gender_female
        else -> Res.drawable.gender_neutral
    }
}

fun getAlignmentImage(alignment: String): DrawableResource {
    return when (alignment) {
        "good" -> Res.drawable.alignment_good
        "bad" -> Res.drawable.alignment_evil
        else -> Res.drawable.alignment_neutral
    }
}







