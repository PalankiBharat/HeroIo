package com.hero.domain.utils

import com.hero.data.remote.api.CustomException
import com.hero.data.utils.Response
import com.hero.data.utils.getRealException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T?): Response<T?> {
    return withContext(dispatcher) {
        try {
            Response.Success(apiCall.invoke())
        } catch (e: Exception) {
            Response.Error(e.getRealException())
        }
    }
}
