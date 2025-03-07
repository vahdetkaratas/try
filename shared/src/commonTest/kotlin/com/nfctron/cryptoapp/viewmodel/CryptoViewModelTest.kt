class CryptoViewModelTest {
    private val repository = mockk<CryptoRepository>()
    private val viewModel = CryptoViewModel(repository)
    
    @Test
    fun `loadTrendingCryptos updates state with data from repository`() = runTest {
        // Given
        val cryptos = listOf(
            CryptoCurrency(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                currentPrice = 50000.0,
                priceChangePercentage24h = 5.0,
                marketCap = 1000000000.0
            )
        )
        coEvery { repository.getTrendingCryptos(any()) } returns cryptos
        
        // When
        viewModel.loadTrendingCryptos()
        
        // Then
        assertEquals(cryptos, viewModel.uiState.value.cryptos)
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.error)
    }
    
    @Test
    fun `toggleFavorite updates repository and reloads data`() = runTest {
        // Given
        coEvery { repository.toggleFavorite(any(), any()) } just Runs
        coEvery { repository.getTrendingCryptos(false) } returns emptyList()
        
        // When
        viewModel.toggleFavorite("bitcoin", true)
        
        // Then
        coVerify { 
            repository.toggleFavorite("bitcoin", true)
            repository.getTrendingCryptos(false)
        }
    }
    
    @Test
    fun `loadTrendingCryptos shows error when repository fails`() = runTest {
        // Given
        coEvery { repository.getTrendingCryptos(any()) } throws Exception("Network error")
        
        // When
        viewModel.loadTrendingCryptos()
        
        // Then
        assertTrue(viewModel.uiState.value.error?.isNotEmpty() == true)
        assertFalse(viewModel.uiState.value.isLoading)
    }
} 