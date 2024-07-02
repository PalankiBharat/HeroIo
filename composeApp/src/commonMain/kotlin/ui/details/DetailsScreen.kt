package ui.details

import ScramblingText
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hero.domain.model.PowerStats
import com.hero.domain.model.Superhero
import com.hero.viewmodels.events.SuperheroDetailsEvents
import com.hero.viewmodels.intents.DetailsPageIntents
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.health
import heroio.composeapp.generated.resources.heart
import heroio.composeapp.generated.resources.muscle
import heroio.composeapp.generated.resources.shield
import heroio.composeapp.generated.resources.speed
import heroio.composeapp.generated.resources.swords
import io.ktor.http.Url
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.navigation.AppNavigation
import ui.navigation.LocalNavigationProvider
import ui.theme.blue
import ui.theme.brighten
import ui.theme.green
import ui.theme.orange
import ui.theme.purple
import ui.theme.red
import ui.theme.yellow
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewmodel: SuperheroDetailsViewmodel = koinViewModel(),
    superheroId: String
) {
    val state = viewmodel.states.collectAsState().value
    val events by viewmodel.events.collectAsState(SuperheroDetailsEvents.None)
    LaunchedEffect(
        key1 = Unit
    ) {
        viewmodel.sendIntents(DetailsPageIntents.SetSelectedSuperhero(superheroId))
    }

    LaunchedEffect(key1 = state.selectedSuperhero) {
        println("Stats " + state.selectedSuperhero?.powerStats.toString())
    }
    state.selectedSuperhero?.let {
        Column(
            modifier = Modifier.background(Color.Black).scrollable(
                state = rememberScrollState(initial = 0),
                orientation = Orientation.Vertical
            )
        ) {
            DetailsPager(
                modifier = Modifier.fillMaxHeight(0.6f),
                superheroList = state.superheroList ?: emptyList(),
                selectedSuperhero = it
            ) {
                viewmodel.sendIntents(DetailsPageIntents.SetSelectedSuperhero(it.id))
            }
            SuperheroDetails(superhero = it)
        }

    }
}

@Composable
fun SuperheroDetails(modifier: Modifier = Modifier, superhero: Superhero) {
    val imageUrl = superhero.imagesEntity?.midImage
    val networkLoader = rememberNetworkLoader()
    val dominantColorState = rememberDominantColorState(loader = networkLoader)
    LaunchedEffect(imageUrl) {
        imageUrl?.let {
            dominantColorState.updateFrom(Url(imageUrl))
        }
    }
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color.Black)
            .scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
    ) {
        superhero.apply {
            Text(
                "Hero Characteristics",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally)
            )
            CharacteristicRow(
                modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                race = race ?: "",
                gender = gender ?: "",
                alignment = alignment ?: "",
                publisher = publisher ?: "",
                color = dominantColorState.color.brighten(0.2f)
            )
            Spacer(modifier = Modifier.height(10.dp))

            CharacterStatsColumn(
                modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                powerStats = powerStats ?: PowerStats(
                    strength = 0,
                    durability = 0,
                    combat = 0,
                    power = 0,
                    speed = 0,
                    intelligence = 0
                )
            )

        }
    }
}

@Composable
fun CharacteristicRow(
    modifier: Modifier = Modifier,
    race: String,
    gender: String,
    alignment: String,
    publisher: String,
    color: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CharacteristicLabel(
            modifier = Modifier.weight(1f),
            value = race, image = painterResource(getRaceImage(race)),
            color = color
        )
        CharacteristicLabel(
            modifier = Modifier.weight(1f),
            value = gender, image = painterResource(getGenderImage(gender)),
            color = color
        )
        CharacteristicLabel(
            modifier = Modifier.weight(1f),
            value = alignment,
            image = painterResource(getAlignmentImage(alignment)),
            color = color
        )
        CharacteristicLabel(
            modifier = Modifier.weight(1f),
            value = publisher,
            image = painterResource(getPublisherImage(publisher)),
            color = color
        )
    }
}

@Composable
fun CharacterStatsColumn(modifier: Modifier = Modifier, powerStats: PowerStats) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatsBar(
                modifier = Modifier.weight(1f),
                progress = powerStats.combat.toSafePercentage(),
                color = yellow,
                image = Res.drawable.swords
            )
            StatsBar(
                modifier = Modifier.weight(1f),
                //took as health
                progress = powerStats.strength.toSafePercentage(),
                color = green,
                image = Res.drawable.health
            )
        }
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatsBar(
                modifier = Modifier.weight(1f),
                progress = powerStats.speed.toSafePercentage(),
                color = purple,
                image = Res.drawable.speed
            )
            StatsBar(
                modifier = Modifier.weight(1f),
                progress = powerStats.intelligence.toSafePercentage(),
                color = blue,
                image = Res.drawable.shield
            )
        }
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatsBar(
                modifier = Modifier.weight(1f),
                progress = powerStats.combat.toSafePercentage(),
                color = orange,
                image = Res.drawable.muscle
            )
            StatsBar(
                modifier = Modifier.weight(1f),
                progress = powerStats.durability.toSafePercentage(),
                color = red,
                image = Res.drawable.heart
            )
        }
    }
}

fun Int?.toSafePercentage(): Float = this?.toFloat()?.coerceAtMost(100f)?.div(100f) ?: 0f


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsPager(
    modifier: Modifier = Modifier,
    superheroList: List<Superhero>,
    selectedSuperhero: Superhero,
    onHeroChange: (Superhero) -> Unit
) {
    val navController = LocalNavigationProvider.current
    val selectedIndex = superheroList.indexOfFirst { it == selectedSuperhero }
    val pagerState = rememberPagerState(initialPage = selectedIndex, pageCount = {
        superheroList.count()
    })
    var offsetY by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = pagerState.currentPage)
    {
        onHeroChange(superheroList.getOrElse(pagerState.currentPage) {
            selectedSuperhero
        }
        )
    }

    Box {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                    ),
                    size = size
                )
            }.pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        offsetY = offset.y
                    }
                )
            },
            key = {
                superheroList[it].id
            },
        ) { page ->
            val image = superheroList[page].imagesEntity?.largeImage
            Box {
                image?.let {
                    AsyncImage(
                        model = image,
                        contentDescription = image,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                            .graphicsLayer {
                                val pageOffset = pagerState.offsetForPage(page)
                                translationX = size.width * pageOffset

                                val endOffset = pagerState.endOffsetForPage(page)
                                shape = CirclePath(
                                    progress = 1f - endOffset.absoluteValue,
                                    origin = Offset(
                                        size.width,
                                        offsetY,
                                    )
                                )
                                clip = true

                                val absoluteOffset = pagerState.offsetForPage(page).absoluteValue
                                val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                                scaleX = scale
                                scaleY = scale

                                val startOffset = pagerState.startOffsetForPage(page)
                                alpha = (2f - startOffset) / 2f
                            }
                    )
                }
            }
        }

        ScramblingText(
            modifier = Modifier.align(Alignment.BottomStart).padding(20.dp),
            data = listOf(
                selectedSuperhero.name,
                selectedSuperhero.fullName ?: "",
            )
        )

        FloatingActionButton(onClick = {
            navController.navigate(
                route = AppNavigation.Chat.createRouteWithArgs(
                    args = AppNavigation.Chat.ChatArguments(
                        id = selectedSuperhero.id,
                        name = selectedSuperhero.name,
                        img = selectedSuperhero.imagesEntity?.midImage ?: ""
                    )
                )
            )
        }) {
            Icon(imageVector = Icons.Filled.ChatBubbleOutline, contentDescription = "")
        }
    }

}


// ACTUAL OFFSET
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

// OFFSET ONLY FROM THE LEFT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtLeast(0f)
}

// OFFSET ONLY FROM THE RIGHT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.endOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtMost(0f)
}


class CirclePath(val progress: Float, val origin: Offset = Offset(0f, 0f)) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {

        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = (sqrt(
            size.height * size.height + size.width * size.width
        ) * .5f) * progress
        println("Center - " + center + "Offset Y - " + origin.y)
        return Outline.Generic(Path().apply {
            addOval(
                Rect(
                    center = center,
                    radius = radius,
                )
            )
        })
    }
}


