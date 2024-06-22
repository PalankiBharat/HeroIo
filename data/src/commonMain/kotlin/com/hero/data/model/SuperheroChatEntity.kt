package com.hero.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuperheroChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val superheroID: String,
    val role: String,
    val message: String
)