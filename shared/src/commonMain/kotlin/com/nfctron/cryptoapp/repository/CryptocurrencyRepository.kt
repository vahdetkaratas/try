package com.nfctron.cryptoapp.repository

import com.nfctron.cryptoapp.api.CoinGeckoApi
import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.db.CryptocurrencyDatabase
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyRepository {
    suspend fun getTrendingCryptocurrencies(): List<CryptoCurrency>
    suspend fun searchCryptos(query: String): List<CryptoCurrency>
    suspend fun toggleFavorite(id: String)
    fun getFavorites(): Flow<List<CryptoCurrency>>
}

class CryptocurrencyRepositoryImpl(
    private val api: CoinGeckoApi,
    private val database: CryptocurrencyDatabase
) : CryptocurrencyRepository {
    // ... implementation ...
    override suspend fun getTrendingCryptocurrencies(): List<CryptoCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun searchCryptos(query: String): List<CryptoCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(id: String) {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<List<CryptoCurrency>> {
        TODO("Not yet implemented")
    }
} 