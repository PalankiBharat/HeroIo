package com.hero.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform