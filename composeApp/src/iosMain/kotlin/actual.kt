
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.SuperheroDatabase
import data.local.dbFileName
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


actual fun httpClient(config: HttpClientConfig<*>.()->Unit) = HttpClient(Darwin){
    config(this)
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}

actual fun platformModule() = module {
    single<SuperheroDatabase> { createRoomDatabase() }
}


fun createRoomDatabase(): SuperheroDatabase {
    val dbFile = "${fileDirectory()}/$dbFileName"
    return Room.databaseBuilder<SuperheroDatabase>(
        name = dbFile,
        factory =  { SuperheroDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}


@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}


