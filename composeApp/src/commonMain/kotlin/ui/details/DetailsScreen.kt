package ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero
import com.hero.viewmodels.intents.DetailsPageIntents
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

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
        DetailsPager(
            modifier = modifier,
            superheroList = state.superheroList ?: emptyList(),
            selectedSuperhero = it
        )
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

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        key = {
            superheroList[it].id
        }
    ) { page ->
        val image = superheroList[page].imagesEntity?.largeImage
        image?.let {
            SuperheroPagerImage(image = it, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperheroPagerImage(
    modifier: Modifier = Modifier,
    image: String,
    pagerState: PagerState
) {
    AsyncImage(
        model = image,
        contentDescription = image,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}