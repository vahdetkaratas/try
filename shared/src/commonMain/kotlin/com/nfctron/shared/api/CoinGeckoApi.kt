package com.nfctron.shared.api

import com.nfctron.shared.domain.models.Cryptocurrency
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface CoinGeckoApi {
    suspend fun getTrendingCoins(): List<Cryptocurrency>
    suspend fun searchCoins(query: String): List<Cryptocurrency>
}

class CoinGeckoApiImpl(
    private val client: HttpClient
) : CoinGeckoApi {
    private val baseUrl = "https://api.coingecko.com/api/v3"

    override suspend fun getTrendingCoins(): List<Cryptocurrency> =
        client.get("$baseUrl/coins/markets") {
            parameter("vs_currency", "usd")
            parameter("order", "market_cap_desc")
            parameter("per_page", "20")
            parameter("sparkline", "false")
        }.body()

    override suspend fun searchCoins(query: String): List<Cryptocurrency> =
        client.get("$baseUrl/search") {
            parameter("query", query)
        }.body()
} 