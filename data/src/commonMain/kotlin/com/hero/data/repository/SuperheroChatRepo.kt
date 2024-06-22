package com.hero.data.repository

import SuperheroChatResponse
import com.hero.data.model.SuperheroChatRequest
import com.hero.data.model.SuperheroEntity
import kotlinx.coroutines.flow.Flow


interface SuperheroChatRepository {
    fun getSuperheroChat(superheroID:String): Flow<List<SuperheroEntity>>
    suspend fun sendChatToServer(superheroChatRequest: SuperheroChatRequest, superheroID:String)
}