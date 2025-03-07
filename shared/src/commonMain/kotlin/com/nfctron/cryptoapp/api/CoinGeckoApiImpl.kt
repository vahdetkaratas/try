class CoinGeckoApiImpl(private val client: HttpClient) : CoinGeckoApi {
    private val baseUrl = "https://api.coingecko.com/api/v3"

    override suspend fun getTrendingCryptos(): List<CryptoCurrency> {
        return try {
            client.get("$baseUrl/coins/markets") {
                parameter("vs_currency", "usd")
                parameter("order", "market_cap_desc")
                parameter("per_page", "20")
                parameter("sparkline", "true")
            }.body<List<CoinGeckoResponse>>().map { it.toCryptoCurrency() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchCryptos(query: String): List<CryptoCurrency> {
        return try {
            client.get("$baseUrl/search") {
                parameter("query", query)
            }.body<List<CoinGeckoResponse>>().map { it.toCryptoCurrency() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCryptoDetails(id: String): CryptoCurrency {
        return client.get("$baseUrl/coins/$id").body<CoinGeckoResponse>().toCryptoCurrency()
    }
} 