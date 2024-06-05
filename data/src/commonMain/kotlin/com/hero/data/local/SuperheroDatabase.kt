package com.hero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hero.data.model.SuperheroEntity

@Database(entities = [SuperheroEntity::class], version = 2)
@TypeConverters(StringListConverter::class,PowerStatsConverter::class,ImagesConverter::class)
abstract class SuperheroDatabase : RoomDatabase(), DB {
    abstract fun superheroDao(): SuperheroDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}


// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}
