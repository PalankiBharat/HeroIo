package com.hero.data.remote.api

import SuperheroChatResponse
import com.hero.data.model.SuperheroChatRequest
import com.hero.data.utils.ApiConstants.BASE_URL
import com.hero.data.utils.ApiConstants.BY_ID
import com.hero.data.utils.ApiConstants.CHAT_BASE_URL
import com.hero.data.utils.ApiConstants.GROQ_API_KEY
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SuperheroChat:SuperheroChatApiService {
    private val httpClient = HttpClient.httpClient

    override suspend fun sendSuperheroChat(superheroChatRequest: SuperheroChatRequest): SuperheroChatResponse {
        val result = try {
            httpClient.post {
                url(CHAT_BASE_URL)
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $GROQ_API_KEY")
                setBody(superheroChatRequest)
            }
        } catch (e: Exception) {
            throw CustomException(CustomError.SERVICE_UNAVAILABLE)
        }
        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw CustomException(CustomError.SERVER_ERROR)
            in 400..499 -> throw CustomException(CustomError.CLIENT_ERROR)
            else -> throw CustomException(CustomError.UNKNOWN_ERROR)
        }
        return try {
            result.body()
        } catch (e: Exception) {
            throw CustomException(CustomError.SERVER_ERROR)
        }
    }
}