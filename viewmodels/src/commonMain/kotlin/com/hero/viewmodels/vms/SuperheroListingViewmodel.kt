package com.hero.viewmodels.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hero.data.utils.onError
import com.hero.data.utils.onSuccess
import com.hero.domain.useCases.SuperheroUseCase
import com.hero.viewmodels.events.SuperheroListingEvents
import com.hero.viewmodels.intents.SuperheroListingIntents
import com.hero.viewmodels.viewStates.SuperheroListingViewStates
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class SuperheroListingViewmodel(
    private val superheroUseCase: SuperheroUseCase
) : ViewModel(), KoinComponent {

    private val _states = MutableStateFlow(SuperheroListingViewStates("", false, emptyList()))
    val states: StateFlow<SuperheroListingViewStates> = _states

    private val _events = MutableSharedFlow<SuperheroListingEvents>()
    val events: SharedFlow<SuperheroListingEvents> = _events

    init {
        viewModelScope.launch {
            sendIntents(SuperheroListingIntents.GetDataFromNetwork)
            superheroUseCase.getSuperheroList().collectLatest {
                it.onError {
                    _events.emit(SuperheroListingEvents.OnError(it.message))
                }.onSuccess { result ->
                    _states.update { state ->
                        println("Max Power " + result.map {
                            it.powerStats?.power ?: 0
                        }.maxOrNull())
                        println("Max Power " + result.map {
                            it.powerStats?.speed ?: 0
                        }.maxOrNull())
                        println("Max Power " + result.map {
                            it.powerStats?.strength ?: 0
                        }.maxOrNull())
                        println("Max Power " + result.map {
                            it.powerStats?.intelligence ?: 0
                        }.maxOrNull())
                        println("Max Power " + result.map {
                            it.powerStats?.durability ?: 0
                        }.maxOrNull())
                        println("Max Power " + result.maxOfOrNull {
                            it.powerStats?.combat ?: 0
                        })
                        state.copy(superheroList = result)
                    }
                }
            }
        }
    }


    fun sendIntents(events: SuperheroListingIntents) {
        viewModelScope.launch {
            when (events) {
                SuperheroListingIntents.GetDataFromNetwork -> {
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
                        _events.emit(SuperheroListingEvents.OnError(it.message))
                    }
                }

                is SuperheroListingIntents.OnSearchEvent -> {
                    _states.update {
                        it.copy(searchQuery = events.query)
                    }
                }
            }
        }

    }

}