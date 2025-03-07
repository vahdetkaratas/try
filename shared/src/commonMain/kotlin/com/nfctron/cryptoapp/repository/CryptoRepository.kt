import com.nfctron.cryptoapp.api.CoinGeckoApi
import com.nfctron.cryptoapp.db.CryptoDatabase
import com.nfctron.cryptoapp.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoRepository(
    private val api: CoinGeckoApi,
    private val database: CryptoDatabase
) {
    suspend fun getTrendingCryptos(forceRefresh: Boolean = false): Result<List<CryptoCurrency>> {
        return try {
            if (forceRefresh) {
                val cryptos = api.getTrendingCryptos()
                cryptos.forEach { crypto ->
                    database.cryptoCurrencyQueries.insertCrypto(
                        id = crypto.id,
                        symbol = crypto.symbol,
                        name = crypto.name,
                        current_price = crypto.currentPrice,
                        price_change_24h = crypto.priceChangePercentage24h,
                        market_cap = crypto.marketCap,
                        is_favorite = if (crypto.isFavorite) 1L else 0L
                    )
                }
            }
            Result.success(database.cryptoCurrencyQueries.getAllCryptos().executeAsList().map { it.toCryptoCurrency() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFavorites(): Result<List<CryptoCurrency>> {
        return try {
            Result.success(database.cryptoCurrencyQueries.getFavorites().executeAsList().map { it.toCryptoCurrency() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun toggleFavorite(cryptoId: String, isFavorite: Boolean) {
        database.cryptoCurrencyQueries.updateFavorite(
            is_favorite = if (isFavorite) 1L else 0L,
            id = cryptoId
        )
    }

    suspend fun searchCryptos(query: String): Result<List<CryptoCurrency>> {
        return try {
            Result.success(
                database.cryptoCurrencyQueries.searchCryptos(query, query)
                    .executeAsList()
                    .map { it.toCryptoCurrency() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 