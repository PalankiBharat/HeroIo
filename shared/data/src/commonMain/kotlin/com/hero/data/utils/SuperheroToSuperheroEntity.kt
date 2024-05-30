package com.hero.data.utils

import com.hero.data.model.Superhero
import com.hero.data.model.SuperheroEntity

fun Superhero.toSuperheroEntity(): SuperheroEntity {
    return SuperheroEntity(
        id = this.id.toString(),
        name = this.name ?: "",
        strength = this.powerstats?.strength,
        durability = this.powerstats?.durability,
        combat = this.powerstats?.combat,
        power = this.powerstats?.power,
        speed = this.powerstats?.speed,
        intelligence = this.powerstats?.intelligence,
        gender = this.appearance?.gender,
        race = this.appearance?.race,
        eyeColor = this.appearance?.eyeColor,
        weight = this.appearance?.weight,
        hairColor = this.appearance?.hairColor,
        height = this.appearance?.height,
        image = this.images?.lg,
        firstAppearance = this.biography?.firstAppearance,
        placeOfBirth = this.biography?.placeOfBirth,
        aliases = this.biography?.aliases,
        fullName = this.biography?.fullName,
        publisher = this.biography?.publisher,
        alterEgos = this.biography?.alterEgos,
        alignment = this.biography?.alignment
    )
}