package com.nfctron.cryptoapp.db

import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.db.Cryptocurrency

fun Cryptocurrency.toDomain(): CryptoCurrency {
    return CryptoCurrency(
        id = id,
        name = name,
        symbol = symbol,
        currentPrice = current_price,
        priceChange24h = price_change_24h,
        imageUrl = image_url,
        isFavorite = is_favorite == 1L
    )
}

fun CryptoCurrency.toEntity(): Cryptocurrency {
    return Cryptocurrency(
        id = id,
        name = name,
        symbol = symbol,
        current_price = currentPrice,
        price_change_24h = priceChange24h,
        image_url = imageUrl,
        is_favorite = if (isFavorite) 1L else 0L
    )
} 