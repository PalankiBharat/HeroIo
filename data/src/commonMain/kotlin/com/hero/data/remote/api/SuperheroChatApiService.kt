package com.hero.data.remote.api

import SuperheroChatResponse
import com.hero.data.model.SuperheroChatRequest

fun interface SuperheroChatApiService {
    suspend fun sendSuperheroChat(superheroChatRequest: SuperheroChatRequest): SuperheroChatResponse
}