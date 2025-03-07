package com.nfctron.cryptoapp.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String

public class CryptoDatabaseQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> getAllCryptos(mapper: (
    id: String,
    symbol: String,
    name: String,
    current_price: Double,
    price_change_24h: Double,
    market_cap: Double,
    is_favorite: Long,
  ) -> T): Query<T> = Query(163_309_154, arrayOf("CryptoCurrency"), driver, "CryptoDatabase.sq",
      "getAllCryptos", """
  |SELECT *
  |FROM CryptoCurrency
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getDouble(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun getAllCryptos(): Query<CryptoCurrency> = getAllCryptos { id, symbol, name,
      current_price, price_change_24h, market_cap, is_favorite ->
    CryptoCurrency(
      id,
      symbol,
      name,
      current_price,
      price_change_24h,
      market_cap,
      is_favorite
    )
  }

  public fun <T : Any> getCryptoById(id: String, mapper: (
    id: String,
    symbol: String,
    name: String,
    current_price: Double,
    price_change_24h: Double,
    market_cap: Double,
    is_favorite: Long,
  ) -> T): Query<T> = GetCryptoByIdQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getDouble(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun getCryptoById(id: String): Query<CryptoCurrency> = getCryptoById(id) { id_, symbol,
      name, current_price, price_change_24h, market_cap, is_favorite ->
    CryptoCurrency(
      id_,
      symbol,
      name,
      current_price,
      price_change_24h,
      market_cap,
      is_favorite
    )
  }

  public fun <T : Any> getFavorites(mapper: (
    id: String,
    symbol: String,
    name: String,
    current_price: Double,
    price_change_24h: Double,
    market_cap: Double,
    is_favorite: Long,
  ) -> T): Query<T> = Query(-1_442_991_930, arrayOf("CryptoCurrency"), driver, "CryptoDatabase.sq",
      "getFavorites", """
  |SELECT *
  |FROM CryptoCurrency
  |WHERE is_favorite = 1
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getDouble(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun getFavorites(): Query<CryptoCurrency> = getFavorites { id, symbol, name, current_price,
      price_change_24h, market_cap, is_favorite ->
    CryptoCurrency(
      id,
      symbol,
      name,
      current_price,
      price_change_24h,
      market_cap,
      is_favorite
    )
  }

  public fun <T : Any> searchCryptos(
    `value`: String,
    value_: String,
    mapper: (
      id: String,
      symbol: String,
      name: String,
      current_price: Double,
      price_change_24h: Double,
      market_cap: Double,
      is_favorite: Long,
    ) -> T,
  ): Query<T> = SearchCryptosQuery(value, value_) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getDouble(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun searchCryptos(value_: String, value__: String): Query<CryptoCurrency> =
      searchCryptos(value_, value__) { id, symbol, name, current_price, price_change_24h,
      market_cap, is_favorite ->
    CryptoCurrency(
      id,
      symbol,
      name,
      current_price,
      price_change_24h,
      market_cap,
      is_favorite
    )
  }

  public fun insertCrypto(
    id: String,
    symbol: String,
    name: String,
    current_price: Double,
    price_change_24h: Double,
    market_cap: Double,
    is_favorite: Long,
  ) {
    driver.execute(646_235_647, """
        |INSERT OR REPLACE INTO CryptoCurrency(id, symbol, name, current_price, price_change_24h, market_cap, is_favorite)
        |VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 7) {
          bindString(0, id)
          bindString(1, symbol)
          bindString(2, name)
          bindDouble(3, current_price)
          bindDouble(4, price_change_24h)
          bindDouble(5, market_cap)
          bindLong(6, is_favorite)
        }
    notifyQueries(646_235_647) { emit ->
      emit("CryptoCurrency")
    }
  }

  public fun updateFavorite(is_favorite: Long, id: String) {
    driver.execute(748_137_834, """
        |UPDATE CryptoCurrency
        |SET is_favorite = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindLong(0, is_favorite)
          bindString(1, id)
        }
    notifyQueries(748_137_834) { emit ->
      emit("CryptoCurrency")
    }
  }

  private inner class GetCryptoByIdQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("CryptoCurrency", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("CryptoCurrency", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(26_804_132, """
    |SELECT *
    |FROM CryptoCurrency
    |WHERE id = ?
    """.trimMargin(), mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "CryptoDatabase.sq:getCryptoById"
  }

  private inner class SearchCryptosQuery<out T : Any>(
    public val `value`: String,
    public val value_: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("CryptoCurrency", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("CryptoCurrency", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(386_464_901, """
    |SELECT *
    |FROM CryptoCurrency
    |WHERE name LIKE '%' || ? || '%' OR symbol LIKE '%' || ? || '%'
    """.trimMargin(), mapper, 2) {
      bindString(0, value)
      bindString(1, value_)
    }

    override fun toString(): String = "CryptoDatabase.sq:searchCryptos"
  }
}
