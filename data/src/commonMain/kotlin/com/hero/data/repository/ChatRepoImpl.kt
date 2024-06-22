package com.hero.data.repository

import Message
import com.hero.data.local.SuperheroChatDao
import com.hero.data.model.SuperheroChatEntity
import com.hero.data.model.SuperheroChatRequest
import com.hero.data.remote.api.SuperheroChatApiService
import com.hero.data.utils.ApiConstants.GROQ_LLM_MODEL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import org.koin.core.annotation.Single
import toChatEntity

@Single(binds = [ChatRepository::class])
class ChatRepoImpl(
    val apiService: SuperheroChatApiService,
    val dao: SuperheroChatDao
) : ChatRepository {
    override fun getSuperheroChat(superheroId: String): Flow<List<SuperheroChatEntity>> =
        dao.getAllChatsBySuperhero(superheroId)

    override suspend fun sendChatToServer(message: Message, superheroID: String) {
        val oldMessage = getSuperheroChat(superheroID).lastOrNull()?.map {
            Message(
                role = it.role,
                content = it.message
            )
        }
        dao.addChat(message.toChatEntity(superheroID))
        val newList = oldMessage?.toMutableList()
        newList?.add(message)
        val response = apiService.sendSuperheroChat(
            SuperheroChatRequest(
                messageList = newList,
                modelName = GROQ_LLM_MODEL
            )
        )
        val msg = response.choices?.lastOrNull()?.toChatEntity(superheroID)
        if (msg != null) {
            dao.addChat(msg)
        }
    }
}

fun Message.toChatEntity(superheroID: String) = SuperheroChatEntity(
    superheroID = superheroID,
    role = role ?: "",
    message = content ?: ""
)
