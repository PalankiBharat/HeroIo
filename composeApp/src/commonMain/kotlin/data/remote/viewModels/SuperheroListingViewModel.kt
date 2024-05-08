package data.remote.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.SuperheroEntity
import data.remote.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SuperheroListingViewModel(
    private val repository: AppRepository
) : ViewModel(), KoinComponent {

    private val _uiStates = MutableStateFlow(SuperheroListingUIStates())
    val uiStates = _uiStates.asStateFlow()

    init {
        getAllHeroes()
    }

    private fun getAllHeroes() {
        viewModelScope.launch {
            try {
                 repository.getSuperheroList()
                _uiStates.update {
                    it.copy(loading = false)
                }

                repository.getSuperheroesFromLocal().collect { list ->
                    _uiStates.update { it.copy(superheroes = list) }
                }

            } catch (e: Exception) {
                _uiStates.update {
                    it.copy(
                        loading = false,
                        error = e.message ?: "An Unknown Exception Occurred"
                    )
                }
            }
        }
    }
}

data class SuperheroListingUIStates(
    val loading: Boolean = false,
    val superheroes: List<SuperheroEntity?> = arrayListOf(),
    val error: String = ""
)