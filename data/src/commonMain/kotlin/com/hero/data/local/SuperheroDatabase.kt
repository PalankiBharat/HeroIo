package com.hero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hero.data.model.SuperheroChatEntity
import com.hero.data.model.SuperheroEntity

@Database(entities = [SuperheroEntity::class, SuperheroChatEntity::class], version = 1)
@TypeConverters(StringListConverter::class, PowerStatsConverter::class, ImagesConverter::class)
abstract class SuperheroDatabase : RoomDatabase(), DB {
    abstract fun superheroDao(): SuperheroDao
    abstract fun superheroChatDao(): SuperheroChatDao
    override fun clearAllTables() {
        super.clearAllTables()
    }
}


// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}
