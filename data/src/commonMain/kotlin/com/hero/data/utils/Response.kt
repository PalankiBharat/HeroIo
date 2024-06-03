package com.hero.data.utils

import com.hero.data.remote.api.CustomException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

sealed interface Response<out T> {
    data class Success<out T>(val value: T) : Response<T>
    data class Error(val error: CustomError) : Response<Nothing>
}

inline fun <T> Response<T>.onSuccess(onSuccess: (T) -> Unit) {
    if (this is Response.Success) onSuccess(this.value)
}
inline fun <T> Response<T>.onError(onError: (CustomError) -> Unit) {
    if (this is Response.Error) onError(error)
}
inline fun <T> Response<T>.onResult(onSuccess: (T) -> Unit, onError: (CustomError) -> Unit) {
    when(this) {
        is Response.Success -> onSuccess(value)
        is Response.Error -> onError(error)
    }
}

sealed class CustomError(val message: String = "") {
    data object NetworkError : CustomError("Something wrong with network, please try again.")
    data object RandomError : CustomError("Something went wrong, please try again.")
    data object ResponseError : CustomError("We are fixing your problem, Thank you for your patience.")
    data object NoInternet : CustomError("No Internet")
    data object NotFound : CustomError("Not Found")
    data class ExceptionMessage(val error: String) : CustomError(message = error)
}

fun  Exception.getRealException(): CustomError = when (this) {
    is HttpRequestTimeoutException -> {
        CustomError.NetworkError
    }
    is RedirectResponseException -> {
        CustomError.NetworkError
    }
    is ClientRequestException -> {
        CustomError.NetworkError
    }
    is ServerResponseException -> {
        CustomError.ResponseError
    }
    is CustomException ->{
        CustomError.ExceptionMessage(this.message ?: "")
    }
    else -> {
        CustomError.RandomError
    }
}