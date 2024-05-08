import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.SuperheroDatabase
import data.local.dbFileName
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

actual fun httpClient(config: HttpClientConfig<*>.()->Unit) = HttpClient(OkHttp){
    config(this)
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}

actual fun platformModule() = module {
    single<SuperheroDatabase> { createRoomDatabase(get()) }
}


fun createRoomDatabase(ctx: Context): SuperheroDatabase {
    val dbFile = ctx.getDatabasePath(dbFileName)
    return Room.databaseBuilder<SuperheroDatabase>(ctx, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}