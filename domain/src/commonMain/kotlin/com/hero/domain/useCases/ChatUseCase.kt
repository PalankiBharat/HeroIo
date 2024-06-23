package com.hero.domain.useCases

import Message
import com.hero.data.repository.ChatRepository
import com.hero.domain.model.SuperheroChat
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class ChatUseCase(
    private val superheroChatRepository: ChatRepository
) {

    fun getSuperheroChatById(superheroId: String) =
        superheroChatRepository.getSuperheroChat(superheroId).map { it ->
            it.map {
                SuperheroChat(
                    superheroID = superheroId,
                    role = it.role,
                    message = it.message
                )
            }
        }

    suspend fun sendChat(superheroChatEntity: SuperheroChat) {
        superheroChatRepository.sendChatToServer(
            superheroChatRequest = Message(superheroChatEntity.role,superheroChatEntity.message),
            superheroChatEntity.superheroID
        )
    }
}