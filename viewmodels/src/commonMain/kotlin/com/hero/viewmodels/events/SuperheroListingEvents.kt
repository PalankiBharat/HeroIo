package com.hero.viewmodels.events

sealed interface SuperheroListingEvents {
    data class OnError(val error: String) : SuperheroListingEvents
    data object None:SuperheroDetailsEvents
}