package com.nfctron.db

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Cryptocurrency(
  public val id: String,
  public val name: String,
  public val symbol: String,
  public val current_price: Double,
  public val price_change_24h: Double,
  public val image_url: String,
  public val is_favorite: Long,
)
