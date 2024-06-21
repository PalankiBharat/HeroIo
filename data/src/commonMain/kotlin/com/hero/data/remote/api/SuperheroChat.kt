package com.hero.data.remote.api

import SuperheroChatResponse
import com.hero.data.model.SuperheroChatRequest

class SuperheroChat:SuperheroChatApiService {
    override suspend fun sendSuperheroChat(superheroChatRequest: SuperheroChatRequest): SuperheroChatResponse {

    }
}