package com.hero.viewmodels.events

sealed interface SuperheroDetailsEvents {
    data class OnError(val error: String) : SuperheroDetailsEvents
}