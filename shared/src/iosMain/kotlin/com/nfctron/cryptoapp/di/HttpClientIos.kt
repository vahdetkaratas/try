import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun createHttpClient() = HttpClient(Darwin) {
    // ... same configuration as common
} 