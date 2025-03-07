package com.nfctron.cryptoapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val coins: List<CoinSearchResult>,
    val exchanges: List<ExchangeSearchResult> = emptyList(),
    val categories: List<CategorySearchResult> = emptyList(),
    val nfts: List<NftSearchResult> = emptyList()
)

@Serializable
data class CoinSearchResult(
    val id: String,
    val name: String,
    val symbol: String,
    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null,
    @SerialName("thumb")
    val thumbUrl: String? = null,
    @SerialName("large")
    val largeUrl: String? = null
)

@Serializable
data class ExchangeSearchResult(
    val id: String,
    val name: String,
    @SerialName("market_type")
    val marketType: String? = null
)

@Serializable
data class CategorySearchResult(
    val id: Int,
    val name: String
)

@Serializable
data class NftSearchResult(
    val id: String,
    val name: String,
    val symbol: String? = null
) 