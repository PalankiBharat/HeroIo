package com.hero.data.utils

import com.hero.data.model.SuperheroNetworkEntity
import com.hero.data.model.SuperheroEntity

fun SuperheroNetworkEntity.toSuperheroEntity(): SuperheroEntity {
    return SuperheroEntity(
        id = id.toString(),
        name = name ?: "",
        powerStatsNetworkEntity = powerstats,
        gender = appearance?.gender,
        race = appearance?.race,
        eyeColor = appearance?.eyeColor,
        weight = appearance?.weight,
        hairColor = appearance?.hairColor,
        height = appearance?.height,
        imagesNetworkEntity = images,
        firstAppearance = biography?.firstAppearance,
        placeOfBirth = biography?.placeOfBirth,
        aliases = biography?.aliases,
        fullName = biography?.fullName,
        publisher = biography?.publisher,
        alterEgos = biography?.alterEgos,
        alignment = biography?.alignment
    )
}