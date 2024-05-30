package com.hero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hero.data.local.StringListConverter
import com.hero.data.local.SuperheroDao
import data.model.SuperheroEntity

@Database(entities = [SuperheroEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class SuperheroDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
}

