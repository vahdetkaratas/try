class CryptoRepositoryTest {
    private val api = mockk<CoinGeckoApi>()
    private val database = mockk<CryptoDatabase>()
    private val repository = CryptoRepository(api, database)

    @Test
    fun `getTrendingCryptos returns data from API when force refresh is true`() = runTest {
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
        coEvery { api.getTrendingCryptos() } returns cryptos

        // When
        val result = repository.getTrendingCryptos(forceRefresh = true)

        // Then
        assertEquals(cryptos, result)
        coVerify { api.getTrendingCryptos() }
    }
} 