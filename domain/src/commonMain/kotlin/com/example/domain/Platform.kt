package com.example.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform