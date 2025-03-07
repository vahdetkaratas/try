package com.nfctron.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String

public class CryptocurrencyDbQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> getFavoriteCoins(mapper: (
    id: String,
    name: String,
    symbol: String,
    current_price: Double,
    price_change_24h: Double,
    image_url: String,
    is_favorite: Long,
  ) -> T): Query<T> = Query(996_766_455, arrayOf("Cryptocurrency"), driver, "CryptocurrencyDb.sq",
      "getFavoriteCoins", """
  |SELECT *
  |FROM Cryptocurrency
  |WHERE is_favorite = 1
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun getFavoriteCoins(): Query<Cryptocurrency> = getFavoriteCoins { id, name, symbol,
      current_price, price_change_24h, image_url, is_favorite ->
    Cryptocurrency(
      id,
      name,
      symbol,
      current_price,
      price_change_24h,
      image_url,
      is_favorite
    )
  }

  public fun <T : Any> getAllCoins(mapper: (
    id: String,
    name: String,
    symbol: String,
    current_price: Double,
    price_change_24h: Double,
    image_url: String,
    is_favorite: Long,
  ) -> T): Query<T> = Query(228_308_240, arrayOf("Cryptocurrency"), driver, "CryptocurrencyDb.sq",
      "getAllCoins", """
  |SELECT *
  |FROM Cryptocurrency
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun getAllCoins(): Query<Cryptocurrency> = getAllCoins { id, name, symbol, current_price,
      price_change_24h, image_url, is_favorite ->
    Cryptocurrency(
      id,
      name,
      symbol,
      current_price,
      price_change_24h,
      image_url,
      is_favorite
    )
  }

  public fun insertCoin(
    id: String,
    name: String,
    symbol: String,
    current_price: Double,
    price_change_24h: Double,
    image_url: String,
    is_favorite: Long,
  ) {
    driver.execute(1_680_114_961, """
        |INSERT OR REPLACE INTO Cryptocurrency(
        |    id, name, symbol, current_price, price_change_24h, image_url, is_favorite
        |) VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 7) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, symbol)
          bindDouble(3, current_price)
          bindDouble(4, price_change_24h)
          bindString(5, image_url)
          bindLong(6, is_favorite)
        }
    notifyQueries(1_680_114_961) { emit ->
      emit("Cryptocurrency")
    }
  }

  public fun updateFavorite(is_favorite: Long, id: String) {
    driver.execute(1_405_666_860, """
        |UPDATE Cryptocurrency
        |SET is_favorite = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindLong(0, is_favorite)
          bindString(1, id)
        }
    notifyQueries(1_405_666_860) { emit ->
      emit("Cryptocurrency")
    }
  }
}
