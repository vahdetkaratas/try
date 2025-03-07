package com.nfctron.db.shared

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.nfctron.cryptoapp.db.CryptoDatabaseQueries
import com.nfctron.db.CryptocurrencyDatabase
import com.nfctron.db.CryptocurrencyDbQueries
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<CryptocurrencyDatabase>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = CryptocurrencyDatabaseImpl.Schema

internal fun KClass<CryptocurrencyDatabase>.newInstance(driver: SqlDriver): CryptocurrencyDatabase =
    CryptocurrencyDatabaseImpl(driver)

private class CryptocurrencyDatabaseImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), CryptocurrencyDatabase {
  override val cryptoDatabaseQueries: CryptoDatabaseQueries = CryptoDatabaseQueries(driver)

  override val cryptocurrencyDbQueries: CryptocurrencyDbQueries = CryptocurrencyDbQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 2

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE CryptoCurrency (
          |    id TEXT NOT NULL PRIMARY KEY,
          |    symbol TEXT NOT NULL,
          |    name TEXT NOT NULL,
          |    current_price REAL NOT NULL,
          |    price_change_24h REAL NOT NULL,
          |    market_cap REAL NOT NULL,
          |    is_favorite INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE Cryptocurrency (
          |    id TEXT NOT NULL PRIMARY KEY,
          |    name TEXT NOT NULL,
          |    symbol TEXT NOT NULL,
          |    current_price REAL NOT NULL,
          |    price_change_24h REAL NOT NULL,
          |    image_url TEXT NOT NULL,
          |    is_favorite INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    private fun migrateInternal(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
    ): QueryResult.Value<Unit> {
      if (oldVersion <= 1 && newVersion > 1) {
        driver.execute(null, """
            |CREATE TABLE Cryptocurrency (
            |    id TEXT NOT NULL PRIMARY KEY,
            |    name TEXT NOT NULL,
            |    symbol TEXT NOT NULL,
            |    current_price REAL NOT NULL,
            |    price_change_24h REAL NOT NULL,
            |    image_url TEXT NOT NULL,
            |    is_favorite INTEGER NOT NULL DEFAULT 0
            |)
            """.trimMargin(), 0)
      }
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> {
      var lastVersion = oldVersion

      callbacks.filter { it.afterVersion in oldVersion until newVersion }
      .sortedBy { it.afterVersion }
      .forEach { callback ->
        migrateInternal(driver, oldVersion = lastVersion, newVersion = callback.afterVersion + 1)
        callback.block(driver)
        lastVersion = callback.afterVersion + 1
      }

      if (lastVersion < newVersion) {
        migrateInternal(driver, lastVersion, newVersion)
      }
      return QueryResult.Unit
    }
  }
}
