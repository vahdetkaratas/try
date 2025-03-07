package com.nfctron.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cryptocurrency(
    val id: String,
    val name: String,
    val symbol: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerialName("image")
    val imageUrl: String,
    val isFavorite: Boolean = false
) 