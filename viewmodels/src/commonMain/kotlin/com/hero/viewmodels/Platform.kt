package com.hero.viewmodels

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

