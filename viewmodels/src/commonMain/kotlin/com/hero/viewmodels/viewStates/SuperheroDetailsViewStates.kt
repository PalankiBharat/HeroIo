package com.hero.viewmodels.viewStates

import com.hero.domain.model.Superhero

data class SuperheroDetailsViewStates(
    val superheroList: List<Superhero>? = null,
    val selectedSuperhero: Superhero? = null,
    val selectedSuperheroId: String? = null,
)
