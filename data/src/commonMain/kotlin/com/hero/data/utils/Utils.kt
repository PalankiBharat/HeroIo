package com.hero.data.utils

import data.remote.api.CustomException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: CustomMessage) : Result<Nothing>
    data object Loading : Result<Nothing>
    data object Idle : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .onStart { emit(Result.Loading) }
        .catch {
            it.printStackTrace()
            emit(Result.Error((it as Exception).getRealException()))
        }
}

sealed class CustomMessage(val message: String = "") {
    data object NetworkError : CustomMessage("Something wrong with network, please try again.")
    data object RandomError : CustomMessage("Something went wrong, please try again.")
    data object ResponseError : CustomMessage("We are fixing your problem, Thank you for your patience.")
    data object NoInternet : CustomMessage("No Internet")
    data object NotFound : CustomMessage("Not Found")
    data class ExceptionMessage(val error: String) : CustomMessage(message = error)
}

fun  Exception.getRealException(): CustomMessage = when (this) {
    is HttpRequestTimeoutException -> {
        CustomMessage.NetworkError
    }
    is RedirectResponseException -> {
        CustomMessage.NetworkError
    }
    is ClientRequestException -> {
        CustomMessage.NetworkError
    }
    is ServerResponseException -> {
        CustomMessage.ResponseError
    }
    is CustomException ->{
        CustomMessage.ExceptionMessage(this.message ?: "")
    }
    else -> {
        CustomMessage.RandomError
    }
}