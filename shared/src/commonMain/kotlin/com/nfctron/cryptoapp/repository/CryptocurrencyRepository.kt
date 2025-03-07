package com.nfctron.cryptoapp.repository

import com.nfctron.cryptoapp.api.CoinGeckoApi
import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.db.CryptocurrencyDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
    override suspend fun getTrendingCryptocurrencies(): List<CryptoCurrency> {
        return api.getTrendingCryptocurrencies()
    }
    
    override suspend fun searchCryptos(query: String): List<CryptoCurrency> {
        return api.searchCryptos(query)
    }
    
    override suspend fun toggleFavorite(id: String) {
        // Implementation will depend on your database schema
    }
    
    override fun getFavorites(): Flow<List<CryptoCurrency>> {
        return flow {
            // Implementation will depend on your database schema
            emit(emptyList())
        }
    }
} 