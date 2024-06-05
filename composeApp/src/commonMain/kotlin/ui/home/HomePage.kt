package ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(superheroDetailsViewmodel: SuperheroDetailsViewmodel = koinViewModel()) {
    val states = superheroDetailsViewmodel.states.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Row {

        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = states.value.superheroList, key = {
                it.id
            }) {
                SuperheroCard(modifier = Modifier.fillParentMaxHeight(0.2f), superhero = it)
            }

        }
    }


}