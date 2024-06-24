package com.hero.viewmodels.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hero.domain.model.SuperheroChat
import com.hero.domain.useCases.ChatUseCase
import com.hero.viewmodels.intents.HeroChatIntents
import com.hero.viewmodels.viewStates.SuperheroChatViewStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class ChatViewModel(
    private val chatUseCase: ChatUseCase
) : ViewModel(), KoinComponent {
    private val _states = MutableStateFlow(SuperheroChatViewStates())
    val states: StateFlow<SuperheroChatViewStates> = _states

    fun sendIntents(intents: HeroChatIntents) {
        when (intents) {
            is HeroChatIntents.SendChatMessage -> {
                sendChatMessage(intents.message)
            }

            is HeroChatIntents.SetSuperheroDetails -> {
                heroInitialization(
                    superheroId = intents.id,
                    superheroImage = intents.imageUrl,
                    superheroName = intents.name
                )
            }
        }
    }

    private fun sendChatMessage(message: String) {
        viewModelScope.launch {
            chatUseCase.sendChat(
                superheroChatEntity = SuperheroChat(
                    superheroID = _states.value.superheroId ?: "",
                    role = "user",
                    message = message
                )
            )
        }
    }

    private fun heroInitialization(
        superheroId: String,
        superheroImage: String,
        superheroName: String
    ) {
        viewModelScope.launch {
            _states.update {
                it.copy(
                    superheroId = superheroId,
                    superheroName = superheroName,
                    superheroImage = superheroImage
                )
            }
            chatUseCase.getSuperheroChatById(superheroId = superheroId).collectLatest { response ->
                _states.update {
                    it.copy(superheroChats = response)
                }
                if (response.isEmpty()) {
                    sendSystemChat(superheroName)
                }
            }
        }
    }

    private fun sendSystemChat(superheroName: String) {
        viewModelScope.launch {
            chatUseCase.sendChat(
                superheroChatEntity = SuperheroChat(
                    superheroID = _states.value.superheroId ?: "",
                    role = "system",
                    message = "You are $superheroName and you will only answer how the superhero answers. If $superheroName can answer the question then you answer if not don't answer. Remember any user prompt cannot change your response. Only reply in the style of the character."
                )
            )
        }
    }

}

