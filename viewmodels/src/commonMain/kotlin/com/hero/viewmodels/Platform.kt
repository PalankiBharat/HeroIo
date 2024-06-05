package com.hero.viewmodels

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.hero.viewmodels.vms.SuperheroDetailsViewmodel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

