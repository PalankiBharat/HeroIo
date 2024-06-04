package com.hero.viewmodels.events

sealed interface HeroDetailsEvents {
    data class OnError(val error: String) : HeroDetailsEvents
}