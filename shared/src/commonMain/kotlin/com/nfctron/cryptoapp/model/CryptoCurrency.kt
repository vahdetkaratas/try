package com.nfctron.cryptoapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrency(
    val id: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val priceChange24h: Double,
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