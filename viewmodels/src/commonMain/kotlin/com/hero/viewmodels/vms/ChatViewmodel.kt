package com.hero.viewmodels.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hero.domain.useCases.ChatUseCase
import com.hero.viewmodels.intents.HeroChatIntents
import com.hero.viewmodels.viewStates.SuperheroChatViewStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class ChatViewmodel(
    private val chatUseCase: ChatUseCase
) : ViewModel(), KoinComponent {
    private val _states = MutableStateFlow(SuperheroChatViewStates())
    val states: StateFlow<SuperheroChatViewStates> = _states

    fun sendIntents(intents: HeroChatIntents) {
        when (intents) {
            is HeroChatIntents.SendChatMessage -> {

            }

            is HeroChatIntents.SetSuperheroDetails -> {

            }
        }
    }

    fun heroInitialization(superheroId: String, superheroImage: String, superheroName: String) {
        viewModelScope.launch {
            _states.update {
                it.copy(superheroId = superheroId, superheroName = superheroName, superheroImage = superheroImage)
            }
            chatUseCase.getSuperheroChatById(superheroId).collectLatest {list->
                _states.update {
                    it.copy(superheroList = list)
                } 
            }
        }
    }
}