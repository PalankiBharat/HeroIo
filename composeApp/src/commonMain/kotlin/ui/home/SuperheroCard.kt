package ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero

@Composable
fun SuperheroCard(modifier: Modifier = Modifier, superhero: Superhero){

    Card (
        modifier = modifier.fillMaxWidth(),
        shape = AbsoluteRoundedCornerShape(10.dp),
        elevation = 10.dp,
        backgroundColor = Color.White
    ){
        Row {
            AsyncImage(
                model = superhero.imagesEntity?.midImage,
                contentDescription = superhero.name
            )
        }

    }

}


@Composable
fun (modifier: Modifier = Modifier) {

}