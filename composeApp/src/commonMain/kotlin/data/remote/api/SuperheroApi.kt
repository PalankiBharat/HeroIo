package data.remote.api

import data.model.Superhero
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.contentType
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import utils.ApiConstants.BASE_URL
import httpClient
import utils.ApiConstants.BY_ID


class SuperheroApi
{

    private val httpClient = httpClient{
        install(ContentNegotiation){
            json()
        }
    }
    suspend fun getSuperHeroList(): List<Superhero?> {
       /* if (!NetworkUtils().isNetworkAvailable())
        {
            throw NoInternetException()
        }*/
        val result = try {
            httpClient.get {
                url(BASE_URL+"all.json")
                contentType(ContentType.Application.Json)
            }
        }catch (e:Exception)
        {
            throw CustomException(CustomError.SERVICE_UNAVAILABLE)
        }
        when(result.status.value) {
            in 200..299 -> Unit
            500 -> throw CustomException(CustomError.SERVER_ERROR)
            in 400..499 -> throw CustomException(CustomError.CLIENT_ERROR)
            else -> throw CustomException(CustomError.UNKNOWN_ERROR)
        }
        return try {
            result.body()
        } catch(e: Exception) {
            throw CustomException(CustomError.SERVER_ERROR)
        }
    }

    suspend fun getSuperHeroById(id:Int):Superhero?
    {
        val result = try {
            httpClient.get {
                url("$BASE_URL$BY_ID$id.json")
                contentType(ContentType.Application.Json)
            }
        }catch (e:Exception)
        {
            throw CustomException(CustomError.SERVICE_UNAVAILABLE)
        }
        when(result.status.value) {
            in 200..299 -> Unit
            500 -> throw CustomException(CustomError.SERVER_ERROR)
            in 400..499 -> throw CustomException(CustomError.CLIENT_ERROR)
            else -> throw CustomException(CustomError.UNKNOWN_ERROR)
        }
        return try {
            result.body()
        } catch(e: Exception) {
            throw CustomException(CustomError.SERVER_ERROR)
        }
    }
}