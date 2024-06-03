package com.hero.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.hero.data.local.SuperheroDatabase
import com.hero.data.utils.ApiConstants.dbFileName
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun httpClient(config: HttpClientConfig<*>.()->Unit) = HttpClient(Darwin){
    config(this)
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}



fun getSuperheroDatabase(): RoomDatabase.Builder<SuperheroDatabase> {
    val dbFilePath = NSHomeDirectory() + dbFileName
    return Room.databaseBuilder<SuperheroDatabase>(
        name = dbFilePath,
        factory =  { SuperheroDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
}

actual fun platformModules() = module {

}