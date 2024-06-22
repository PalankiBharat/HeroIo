package com.hero.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.hero.data.local.ImagesConverter
import com.hero.data.local.SuperheroDatabase
import com.hero.data.platformModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module


@Module
@ComponentScan("com.hero.data")
class DataModules


val daoModule = module {
    single<SuperheroDatabase> {
        instantiateDatabase {
            get()
        }
    }
    single {
        get<SuperheroDatabase>().superheroDao()
        get<SuperheroDatabase>().superheroChatDao()
    }
    includes(
        listOf(
            DataModules().module,
            platformModules()
        )
    )
}


fun instantiateDatabase(builder: () -> RoomDatabase.Builder<SuperheroDatabase>): SuperheroDatabase {
    return builder()
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .addTypeConverter(ImagesConverter())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}