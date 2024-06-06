package com.hero.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuperheroEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val powerStatsNetworkEntity: PowerStatsNetworkEntity? = null,
    val gender: String? = null,
    val race: String? = null,
    val eyeColor: String? = null,
    val weight: List<String?>? = null,
    val hairColor: String? = null,
    val height: List<String?>? = null,
    val imagesNetworkEntity: ImagesNetworkEntity? = null,
    val firstAppearance: String? = null,
    val placeOfBirth: String? = null,
    val aliases: List<String?>? = null,
    val fullName: String? = null,
    val publisher: String? = null,
    val alterEgos: String? = null,
    val alignment: String? = null
)




