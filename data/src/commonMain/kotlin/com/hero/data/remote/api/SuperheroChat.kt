package com.hero.data.remote.api

import SuperheroChatResponse
import com.hero.data.model.SuperheroChatRequest
import com.hero.data.utils.ApiConstants.CHAT_BASE_URL
import com.hero.data.utils.ApiConstants.GROQ_API_KEY
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Single

@Single(binds = [SuperheroChatApiService::class])
class SuperheroChat : SuperheroChatApiService {
    private val httpClient = HttpClient.httpClient

    override suspend fun sendSuperheroChat(superheroChatRequest: SuperheroChatRequest): SuperheroChatResponse {
        val result = httpClient.post {
                url(CHAT_BASE_URL)
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $GROQ_API_KEY")
                setBody(superheroChatRequest)
            }
        return try {
            result.body()
        } catch (e: Exception) {
            println(e.message)
            throw CustomException(CustomError.SERVER_ERROR)
        }
    }
}