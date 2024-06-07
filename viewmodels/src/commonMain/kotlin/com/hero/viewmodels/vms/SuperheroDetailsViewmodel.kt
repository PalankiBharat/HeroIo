package com.hero.viewmodels.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hero.data.utils.onError
import com.hero.data.utils.onSuccess
import com.hero.domain.useCases.SuperheroUseCase
import com.hero.viewmodels.events.SuperheroDetailsEvents
import com.hero.viewmodels.intents.DetailsPageIntents
import com.hero.viewmodels.viewStates.SuperheroDetailsViewStates
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class SuperheroDetailsViewmodel(
    private val superheroUseCase: SuperheroUseCase
) : ViewModel(), KoinComponent {

    private val _states = MutableStateFlow(SuperheroDetailsViewStates())
    val states: StateFlow<SuperheroDetailsViewStates> = _states

    private val _events = MutableSharedFlow<SuperheroDetailsEvents>()
    val events: SharedFlow<SuperheroDetailsEvents> = _events

    init {
        viewModelScope.launch {
            superheroUseCase.getSuperheroList().collectLatest {
                it.onError {
                    _events.emit(SuperheroDetailsEvents.OnError(it.message))
                }.onSuccess { result ->
                    val superhero =
                        result.find { superhero -> superhero.id == _states.value.selectedSuperheroId }
                    _states.update { state ->
                        state.copy(superheroList = result, selectedSuperhero = superhero)
                    }
                }
            }
        }
    }


    fun sendIntents(events: DetailsPageIntents) {
        viewModelScope.launch {
            when (events) {
                is DetailsPageIntents.SetSelectedSuperhero -> {
                    setSelectedSuperhero(events.id)
                }
            }
        }
    }

    private fun setSelectedSuperhero(id: String) {
        val superhero = _states.value.superheroList?.find { superhero -> superhero.id == id }
        _states.update {
            it.copy(selectedSuperheroId = id, selectedSuperhero = superhero)
        }
    }


}