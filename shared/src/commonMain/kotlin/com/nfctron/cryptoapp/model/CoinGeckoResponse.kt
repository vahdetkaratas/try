package com.nfctron.cryptoapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinGeckoResponse(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("price_change_24h")
    val priceChange24h: Double,
    @SerialName("image")
    val imageUrl: String
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
    priceChangePercentage24h = priceChange24h,
    marketCap = 0.0 // Assuming marketCap is not provided in the new API response
) 