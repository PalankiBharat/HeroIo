package com.hero.data

import androidx.room.RoomDatabase
import com.hero.data.local.SuperheroDatabase
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun platformModules() :Module

