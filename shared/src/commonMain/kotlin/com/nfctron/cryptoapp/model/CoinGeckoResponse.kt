import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinGeckoResponse(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerialName("market_cap")
    val marketCap: Double,
    @SerialName("sparkline_in_7d")
    val sparklineIn7d: SparklineData? = null
)

@Serializable
data class SparklineData(
    val price: List<Double>
)

fun CoinGeckoResponse.toCryptoCurrency() = CryptoCurrency(
    id = id,
    symbol = symbol,
    name = name,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    marketCap = marketCap
) 