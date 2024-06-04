package com.hero.viewmodels.viewStates

import com.hero.domain.model.Superhero

data class SuperheroListingViewStates(
    val searchQuery:String,
    val isLoading:Boolean,
    val superheroList: List<Superhero>
)
