fun CryptoCurrencyEntity.toCryptoCurrency() = CryptoCurrency(
    id = id,
    symbol = symbol,
    name = name,
    currentPrice = current_price,
    priceChangePercentage24h = price_change_24h,
    marketCap = market_cap,
    isFavorite = is_favorite == 1L
) 