package com.nfctron.cryptoapp.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.nfctron.db.CryptocurrencyDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = CryptocurrencyDatabase.Schema,
            context = context,
            name = "cryptocurrency.db"
        )
    }
} 