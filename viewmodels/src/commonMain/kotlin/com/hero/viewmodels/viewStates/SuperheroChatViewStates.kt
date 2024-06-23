package com.hero.viewmodels.viewStates

import com.hero.domain.model.SuperheroChat

data class SuperheroChatViewStates(
    val superheroChats: List<SuperheroChat>? = null,
    val superheroId: String? = null,
    val superheroName: String? = null,
    val superheroImage: String? = null
)
