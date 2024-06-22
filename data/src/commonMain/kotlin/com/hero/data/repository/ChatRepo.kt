package com.hero.data.repository

import Message
import com.hero.data.model.SuperheroChatEntity
import kotlinx.coroutines.flow.Flow


interface ChatRepository {
    fun getSuperheroChat(superheroID:String): Flow<List<SuperheroChatEntity>>
    suspend fun sendChatToServer(superheroChatRequest: Message, superheroID:String)
}