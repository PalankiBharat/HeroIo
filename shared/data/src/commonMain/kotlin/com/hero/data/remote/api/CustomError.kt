package com.hero.data.remote.api

enum class CustomError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class CustomException(serverError: CustomError) : Exception(
     "An error occurred when translating"
)

class NoInternetException : Exception(
    "No Internet"
)