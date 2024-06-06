package com.hero.viewmodels.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hero.data.utils.onError
import com.hero.data.utils.onSuccess
import com.hero.domain.useCases.SuperheroUseCase
import com.hero.viewmodels.events.HeroDetailsEvents
import com.hero.viewmodels.intents.SuperheroDetailsIntents
import com.hero.viewmodels.viewStates.SuperheroListingViewStates
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

    private val _states = MutableStateFlow(SuperheroListingViewStates("", false, emptyList()))
    val states: StateFlow<SuperheroListingViewStates> = _states

    private val _events = MutableSharedFlow<HeroDetailsEvents>()
    val events: SharedFlow<HeroDetailsEvents> = _events

    init {
        viewModelScope.launch {
            sendIntents(SuperheroDetailsIntents.GetDataFromNetwork)
            superheroUseCase.getSuperheroList().collectLatest {
                it.onError {
                    _events.emit(HeroDetailsEvents.OnError(it.message))
                }.onSuccess { result ->
                    _states.update { state ->
                        state.copy(superheroList = result.shuffled())
                    }
                }
            }
        }
    }


    fun sendIntents(events: SuperheroDetailsIntents) {
        viewModelScope.launch {
            when (events) {
                SuperheroDetailsIntents.GetDataFromNetwork -> {
                    _states.update {
                        it.copy(isLoading = true)
                    }
                    superheroUseCase.getSuperhero().onSuccess {
                        _states.update {
                            it.copy(isLoading = false)
                        }
                    }.onError {
                        _states.update {
                            it.copy(isLoading = false)
                        }
                        _events.emit(HeroDetailsEvents.OnError(it.message))
                    }
                }

                is SuperheroDetailsIntents.OnSearchEvent -> {
                    _states.update {
                        it.copy(searchQuery = events.query)
                    }
                }
            }
        }

    }

}