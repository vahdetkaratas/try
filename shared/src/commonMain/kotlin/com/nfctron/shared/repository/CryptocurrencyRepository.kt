package com.nfctron.shared.repository

import com.nfctron.db.CryptocurrencyQueries
import com.nfctron.shared.api.CoinGeckoApi
import com.nfctron.shared.domain.models.Cryptocurrency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CryptocurrencyRepository(
    private val api: CoinGeckoApi,
    private val database: CryptocurrencyQueries
) {
    suspend fun getTrendingCoins(): Flow<List<Cryptocurrency>> = flow {
        try {
            val apiCoins = api.getTrendingCoins()
            database.transaction {
                apiCoins.forEach { coin ->
                    database.insertCoin(
                        id = coin.id,
                        name = coin.name,
                        symbol = coin.symbol,
                        current_price = coin.currentPrice,
                        price_change_24h = coin.priceChangePercentage24h,
                        image_url = coin.imageUrl,
                        is_favorite = if (coin.isFavorite) 1L else 0L
                    )
                }
            }
            emit(apiCoins)
        } catch (e: Exception) {
            // Fallback to database if API fails
            emit(database.getAllCoins().executeAsList().map { 
                Cryptocurrency(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    currentPrice = it.current_price,
                    priceChangePercentage24h = it.price_change_24h,
                    imageUrl = it.image_url,
                    isFavorite = it.is_favorite == 1L
                )
            })
        }
    }

    suspend fun toggleFavorite(coinId: String, isFavorite: Boolean) {
        database.updateFavorite(if (isFavorite) 1L else 0L, coinId)
    }
} 