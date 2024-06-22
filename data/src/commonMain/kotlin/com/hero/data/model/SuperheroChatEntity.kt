package com.hero.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuperheroChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String = "",
    val superheroID: String,
    val role: String,
    val message: String
)