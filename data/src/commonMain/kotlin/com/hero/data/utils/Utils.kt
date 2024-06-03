package com.hero.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: CustomError) : Result<Nothing>
    data object Loading : Result<Nothing>
    data object Idle : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Response<T>> {
    return this
        .map<T, Response<T>> {
            Response.Success(it)
        }
        .catch {
            it.printStackTrace()
            emit(Response.Error((it as Exception).getRealException()))
        }
}

