package com.nfctron.db

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.nfctron.cryptoapp.db.CryptoDatabaseQueries
import com.nfctron.db.shared.newInstance
import com.nfctron.db.shared.schema
import kotlin.Unit

public interface CryptocurrencyDatabase : Transacter {
  public val cryptoDatabaseQueries: CryptoDatabaseQueries

  public val cryptocurrencyDbQueries: CryptocurrencyDbQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = CryptocurrencyDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): CryptocurrencyDatabase =
        CryptocurrencyDatabase::class.newInstance(driver)
  }
}
