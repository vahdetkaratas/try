package com.nfctron.cryptoapp.repository

import com.nfctron.cryptoapp.api.CoinGeckoApi
import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.db.CryptocurrencyDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class CryptoRepositoryTest {
    private val api = mockk<CoinGeckoApi>()
    private val database = mockk<CryptocurrencyDatabase>()
    private val repository = CryptocurrencyRepositoryImpl(api, database)

    @Test
    fun `getTrendingCryptocurrencies returns data from API`() = runTest {
        // Given
        val cryptos = listOf(
            CryptoCurrency(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                currentPrice = 50000.0,
                priceChange24h = 5.0,
                imageUrl = "https://example.com/btc.png",
                isFavorite = false
            )
        )
        coEvery { api.getTrendingCryptocurrencies() } returns cryptos

        // When
        val result = repository.getTrendingCryptocurrencies()

        // Then
        assertEquals(cryptos, result)
        coVerify { api.getTrendingCryptocurrencies() }
    }
} 