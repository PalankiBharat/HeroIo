package ui.details

import ScramblingText
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero
import com.hero.viewmodels.intents.DetailsPageIntents
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.math.absoluteValue
import kotlin.math.log
import kotlin.math.sqrt

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewmodel: SuperheroDetailsViewmodel = koinViewModel(),
    superheroId: String
) {
    val state = viewmodel.states.collectAsState().value
    val events = viewmodel.events
    LaunchedEffect(
        key1 = Unit
    ) {
        viewmodel.sendIntents(DetailsPageIntents.SetSelectedSuperhero(superheroId))
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
            )
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsPager(
    modifier: Modifier = Modifier,
    superheroList: List<Superhero>,
    selectedSuperhero: Superhero
) {

    val selectedIndex = superheroList.indexOfFirst { it == selectedSuperhero }
    val pagerState = rememberPagerState(initialPage = selectedIndex, pageCount = {
        superheroList.count()
    })
    var offsetY by remember { mutableStateOf(0f) }

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
            }   .pointerInput(Unit) {
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


