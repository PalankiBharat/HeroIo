package com.hero.data.utils

sealed interface Response<out T> {

    data class Success<out T>(val value: T) : Response<T>
    data class Error(val error: String) : Response<Nothing>

}

inline fun <T> Response<T>.onSuccess(onSuccess: (T) -> Unit) {
    if (this is Response.Success) onSuccess(this.value)
}
inline fun <T> Response<T>.onError(onError: (String) -> Unit) {
    if (this is Response.Error) onError(error)
}
inline fun <T> Response<T>.onResult(onSuccess: (T) -> Unit, onError: (String) -> Unit) {
    when(this) {
        is Response.Success -> onSuccess(value)
        is Response.Error -> onError(error)
    }
}