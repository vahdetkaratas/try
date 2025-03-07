package com.nfctron.cryptoapp.viewmodel

import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.cryptoapp.repository.CryptocurrencyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest

class CryptoViewModelTest {
    private val repository = mockk<CryptocurrencyRepository>()
    private val viewModel = CryptoViewModel(repository)
    
    @Test
    fun `loadTrendingCoins updates state with data from repository`() = runTest {
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
        coEvery { repository.getTrendingCryptocurrencies() } returns cryptos
        
        // When - init block in ViewModel calls loadTrendingCoins
        
        // Then
        assertEquals(cryptos, viewModel.uiState.value.coins)
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.error)
    }
    
    @Test
    fun `toggleFavorite updates repository and reloads data`() = runTest {
        // Given
        coEvery { repository.toggleFavorite(any()) } just runs
        coEvery { repository.getTrendingCryptocurrencies() } returns emptyList()
        
        // When
        viewModel.toggleFavorite("bitcoin")
        
        // Then
        coVerify { 
            repository.toggleFavorite("bitcoin")
            repository.getTrendingCryptocurrencies()
        }
    }
} 