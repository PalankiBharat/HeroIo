package ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.Image
import coil3.compose.AsyncImage
import com.hero.domain.model.Superhero
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.navigation.AppNavigation

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewmodel: SuperheroDetailsViewmodel = koinViewModel()
) {
    val state = viewmodel.states.value
    val events = viewmodel.events
    DetailsPager(modifier = modifier, superheroList = state.superheroList ?: emptyList())
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsPager(modifier: Modifier = Modifier, superheroList: List<Superhero>) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {
        superheroList.count()
    })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth().aspectRatio(3f / 4f),
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
fun PagerScope.SuperheroPagerImage(modifier: Modifier = Modifier, image: String, pagerState: PagerState) {

    AsyncImage(
        model = image,
        contentDescription = image,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}