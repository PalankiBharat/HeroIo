package ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hero.viewmodels.vms.SuperheroListingViewmodel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.navigation.AppNavigation
import ui.navigation.LocalNavigationProvider


@OptIn(KoinExperimentalAPI::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(superheroListingViewmodel: SuperheroListingViewmodel = koinViewModel()) {
    val navController = LocalNavigationProvider.current
    val states = superheroListingViewmodel.states.collectAsState()
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Row {

        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = states.value.superheroList, key = {
                it.id
            }) {
                SuperheroCardNew(
                    modifier = Modifier.fillParentMaxHeight(0.19f)
                        .animateItemPlacement()
                        .clickable {
                            navController.navigate(
                                route = AppNavigation.Details.createRouteWithId(
                                    it.id
                                )
                            )
                        },
                    superhero = it
                )
            }

        }
    }


}

