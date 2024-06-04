package com.hero.viewmodels.intents

sealed interface SuperheroDetailsIntents {
    data class OnSearchEvent(val query:String):SuperheroDetailsIntents
    data object GetDataFromNetwork : SuperheroDetailsIntents
}