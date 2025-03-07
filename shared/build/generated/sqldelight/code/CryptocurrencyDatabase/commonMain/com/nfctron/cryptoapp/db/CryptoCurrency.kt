package com.nfctron.cryptoapp.db

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class CryptoCurrency(
  public val id: String,
  public val symbol: String,
  public val name: String,
  public val current_price: Double,
  public val price_change_24h: Double,
  public val market_cap: Double,
  public val is_favorite: Long,
)
