package com.nfctron.cryptoapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrency(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("price_change_24h")
    val priceChange24h: Double,
    @SerialName("image")
    val imageUrl: String,
    val isFavorite: Boolean = false
) {
    fun formattedPrice(): String {
        return when {
            currentPrice >= 1000000 -> "%.2fM".format(currentPrice / 1000000)
            currentPrice >= 1000 -> "%.2fK".format(currentPrice / 1000)
            else -> "%.2f".format(currentPrice)
        }
    }

    fun formattedPriceChange(): String {
        return "%+.2f%%".format(priceChange24h)
    }
} 