package ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(superheroDetailsViewmodel: SuperheroDetailsViewmodel = koinViewModel()) {

    val states = superheroDetailsViewmodel.states.collectAsState()

}