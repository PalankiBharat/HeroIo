package ui.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hero.viewmodels.vms.SuperheroListingViewmodel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.navigation.Details
import ui.navigation.LocalNavigationProvider


@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun HomeScreen(
    superheroListingViewmodel: SuperheroListingViewmodel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val navController = LocalNavigationProvider.current
    val states = superheroListingViewmodel.states.collectAsState()
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Row {

        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = states.value.superheroList, key = {
                it.id
            }) {
                with(sharedTransitionScope) {
                    SuperheroCardNew(
                        modifier = Modifier.fillParentMaxHeight(0.19f)
                            .clickable {
                                navController.navigate(Details(id = it.id))
                            },
                        superhero = it,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope
                    )
                }
            }

        }
    }


}

