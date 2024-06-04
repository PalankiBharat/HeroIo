package com.hero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hero.data.model.SuperheroEntity

@Database(entities = [SuperheroEntity::class], version = 2)
@TypeConverters(StringListConverter::class,PowerStatsConverter::class,ImagesConverter::class)
abstract class SuperheroDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
}

