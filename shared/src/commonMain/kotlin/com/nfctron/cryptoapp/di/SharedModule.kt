package com.nfctron.cryptoapp.di

import com.nfctron.cryptoapp.api.CoinGeckoApi
import com.nfctron.cryptoapp.api.CoinGeckoApiImpl
import com.nfctron.cryptoapp.api.createHttpClient
import com.nfctron.cryptoapp.repository.CryptocurrencyRepository
import com.nfctron.cryptoapp.repository.CryptocurrencyRepositoryImpl
import com.nfctron.db.CryptocurrencyDatabase
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun sharedModule() = module {
    single { createHttpClient() }
    single<CoinGeckoApi> { CoinGeckoApiImpl(get()) }
    single { DatabaseDriverFactory(get()).createDriver() }
    single { CryptocurrencyDatabase(get()) }
    single<CryptocurrencyRepository> { CryptocurrencyRepositoryImpl(get(), get()) }
} 