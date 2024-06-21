package com.hero.data.remote.api

import com.hero.data.model.SuperheroNetworkEntity
import com.hero.data.utils.ApiConstants.ALL_SUPERHEROES
import com.hero.data.utils.ApiConstants.BASE_URL
import com.hero.data.utils.ApiConstants.BY_ID
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Single

@Single(binds = [SuperheroApiService::class])
class SuperheroApi : SuperheroApiService {
    private val httpClient = HttpClient.httpClient

    override suspend fun getSuperHeroList(): List<SuperheroNetworkEntity?> {
        val result = httpClient.get {
            url(BASE_URL + ALL_SUPERHEROES)
            contentType(ContentType.Application.Json)
        }
        return try {
            result.body()
        } catch (e: Exception) {
            throw CustomException(CustomError.SERVER_ERROR)
        }
    }

    override suspend fun getSuperHeroById(id: Int): SuperheroNetworkEntity? {
        val result = try {
            httpClient.get {
                url("$BASE_URL$BY_ID$id.json")
                contentType(ContentType.Application.Json)
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