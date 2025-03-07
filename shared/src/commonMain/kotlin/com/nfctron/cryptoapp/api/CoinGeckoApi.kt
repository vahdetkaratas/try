import com.nfctron.cryptoapp.model.CryptoCurrency
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface CoinGeckoApi {
    suspend fun getTrendingCryptos(): List<CryptoCurrency>
    suspend fun searchCryptos(query: String): List<CryptoCurrency>
    suspend fun getCryptoDetails(id: String): CryptoCurrency
} 