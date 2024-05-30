package com.hero.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hero.data.local.StringListConverter

@Entity
data class SuperheroEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val strength: Int? = null,
    val durability: Int? = null,
    val combat: Int? = null,
    val power: Int? = null,
    val speed: Int? = null,
    val intelligence: Int? = null,
    val gender: String? = null,
    val race: String? = null,
    val eyeColor: String? = null,
    @TypeConverters(StringListConverter::class)
    val weight: List<String?>? = null,
    val hairColor: String? = null,
    @TypeConverters(StringListConverter::class)
    val height: List<String?>? = null,
    val image:String? = null,
    val firstAppearance: String? = null,
    val placeOfBirth: String? = null,
    @TypeConverters(StringListConverter::class)
    val aliases: List<String?>? = null,
    val fullName: String? = null,
    val publisher: String? = null,
    val alterEgos: String? = null,
    val alignment: String? = null
)

