package com.hero.domain.model

import com.hero.data.model.ImagesEntity

data class Superhero(
    val id: String,
    val name: String,
    val powerStats: PowerStats? = null,
    val gender: String? = null,
    val race: String? = null,
    val eyeColor: String? = null,
    val weight: List<String?>? = null,
    val hairColor: String? = null,
    val height: List<String?>? = null,
    val imagesEntity: Images? = null,
    val firstAppearance: String? = null,
    val placeOfBirth: String? = null,
    val aliases: List<String?>? = null,
    val fullName: String? = null,
    val publisher: String? = null,
    val alterEgos: String? = null,
    val alignment: String? = null
)

data class PowerStats(
    val strength: Int? = null,
    val durability: Int? = null,
    val combat: Int? = null,
    val power: Int? = null,
    val speed: Int? = null,
    val intelligence: Int? = null
)

data class Images(
    val smallImage: String? = null,
    val midImage: String? = null,
    val largeImage: String? = null,
)

