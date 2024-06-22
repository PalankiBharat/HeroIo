package com.hero.data.repository

import SuperheroChatResponse
import com.hero.data.local.SuperheroChatDao
import com.hero.data.model.SuperheroChatRequest
import com.hero.data.model.SuperheroEntity
import com.hero.data.remote.api.SuperheroChatApiService
import kotlinx.coroutines.flow.Flow
import toChatEntity

class SuperheroChatRepoImpl(
    val apiService: SuperheroChatApiService,
    val dao: SuperheroChatDao
) :SuperheroChatRepository{
    override fun getSuperheroChat(superheroId:String): Flow<List<SuperheroEntity>> = dao.getAllChatsBySuperhero(superheroId)

    override suspend fun sendChatToServer(superheroChatRequest: SuperheroChatRequest,superheroID:String) {
        val response = apiService.sendSuperheroChat(superheroChatRequest)
        val msg = response.choices?.lastOrNull()?.toChatEntity(superheroID)
        if (msg != null) {
            dao.addChat(msg)
        }
    }

}
