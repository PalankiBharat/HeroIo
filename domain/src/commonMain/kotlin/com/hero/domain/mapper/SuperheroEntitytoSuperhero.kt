package com.hero.domain.mapper

import com.hero.data.model.SuperheroEntity
import com.hero.domain.model.Superhero


fun SuperheroEntity.toSuperhero(): Superhero =
    Superhero(
        id = id,
        name = name,
        powerStats = powerStatsNetworkEntity?.toPowerStats(),
        gender = gender,
        race = race,
        eyeColor = eyeColor,
        weight = weight,
        hairColor = hairColor,
        height = height,
        imagesEntity = imagesNetworkEntity?.toImages(),
        firstAppearance = firstAppearance,
        placeOfBirth = placeOfBirth,
        aliases = aliases,
        fullName = fullName,
        publisher = publisher,
        alterEgos = alterEgos,
        alignment = alignment
    )

