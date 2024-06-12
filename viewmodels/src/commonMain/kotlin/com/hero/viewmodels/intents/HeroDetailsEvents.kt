package com.hero.viewmodels.intents

sealed interface SuperheroListingIntents {
    data class OnSearchEvent(val query:String):SuperheroListingIntents
    data object GetDataFromNetwork : SuperheroListingIntents
}