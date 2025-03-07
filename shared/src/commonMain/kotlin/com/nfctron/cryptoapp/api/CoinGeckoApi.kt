package com.nfctron.cryptoapp.api

import com.nfctron.cryptoapp.model.CoinGeckoResponse
import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.cryptoapp.model.SearchResponse
import com.nfctron.cryptoapp.model.toCryptocurrency
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

interface CoinGeckoApi {
    suspend fun getTrendingCryptocurrencies(): List<CryptoCurrency>
    suspend fun searchCryptos(query: String): List<CryptoCurrency>
    suspend fun getCryptoDetails(id: String): CryptoCurrency
}

class CoinGeckoApiImpl(
    private val client: HttpClient
) : CoinGeckoApi {
    private val baseUrl = "https://api.coingecko.com/api/v3"

    override suspend fun getTrendingCryptocurrencies(): List<CryptoCurrency> {
        return client.get("$baseUrl/coins/markets") {
            url {
                parameters.append("vs_currency", "usd")
                parameters.append("order", "market_cap_desc")
                parameters.append("per_page", "20")
                parameters.append("page", "1")
                parameters.append("sparkline", "false")
            }
        }.body<List<CoinGeckoResponse>>().map { it.toCryptocurrency() }
    }

    override suspend fun searchCryptos(query: String): List<CryptoCurrency> {
        return try {
            val searchResponse = client.get("$baseUrl/search") {
                url {
                    parameters.append("query", query)
                }
            }.body<SearchResponse>()

            // Get full details for top 10 results
            searchResponse.coins
                .take(10)
                .mapNotNull { searchResult ->
                    try {
                        getCryptoDetails(searchResult.id)
                    } catch (e: Exception) {
                        null
                    }
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCryptoDetails(id: String): CryptoCurrency {
        return client.get("$baseUrl/coins/$id") {
            url {
                parameters.append("localization", "false")
                parameters.append("tickers", "false")
                parameters.append("market_data", "true")
                parameters.append("community_data", "false")
                parameters.append("developer_data", "false")
                parameters.append("sparkline", "false")
            }
        }.body<CoinGeckoResponse>().toCryptocurrency()
    }
} 