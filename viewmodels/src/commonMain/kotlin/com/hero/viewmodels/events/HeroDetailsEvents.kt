package com.hero.viewmodels.events

sealed interface HeroDetailsEvents {
    data class OnSearchEvent(val query:String):HeroDetailsEvents
    data object GetDataFromNetwork : HeroDetailsEvents
}