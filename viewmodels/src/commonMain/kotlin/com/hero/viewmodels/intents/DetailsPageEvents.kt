package com.hero.viewmodels.intents

import com.hero.domain.model.Superhero

sealed interface DetailsPageIntents {
    data class SetSelectedSuperhero(val id: String): DetailsPageIntents
}