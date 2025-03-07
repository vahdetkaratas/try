package com.nfctron.cryptoapp.model

fun CoinGeckoResponse.toCryptoCurrency(): CryptoCurrency {
    return CryptoCurrency(
        id = id,
        symbol = symbol,
        name = name,
        currentPrice = currentPrice,
        priceChange24h = priceChange24h,
        imageUrl = imageUrl,
        isFavorite = false
    )
} 