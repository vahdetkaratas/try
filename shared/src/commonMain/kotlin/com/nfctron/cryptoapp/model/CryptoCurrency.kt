import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrency(
    val id: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,
    val marketCap: Double,
    val isFavorite: Boolean = false,
    val sparklineIn7d: SparklineData? = null
) {
    fun formattedPrice(): String {
        return when {
            currentPrice >= 1000000 -> "%.2fM".format(currentPrice / 1000000)
            currentPrice >= 1000 -> "%.2fK".format(currentPrice / 1000)
            else -> "%.2f".format(currentPrice)
        }
    }

    fun formattedPriceChange(): String {
        return "%+.2f%%".format(priceChangePercentage24h)
    }
} 