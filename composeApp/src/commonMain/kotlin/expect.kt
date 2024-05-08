import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import org.koin.core.module.Module

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun platformModule(): Module