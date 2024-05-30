package com.hero.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hero.data.local.SuperheroDatabase
import com.hero.data.utils.ApiConstants.dbFileName
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun httpClient(config: HttpClientConfig<*>.()->Unit) = HttpClient(OkHttp){
    config(this)
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}

 fun getSuperheroDatabase(context:Context): RoomDatabase.Builder<SuperheroDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(dbFileName)
    return Room.databaseBuilder<SuperheroDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

actual fun platformModules() = module {

}