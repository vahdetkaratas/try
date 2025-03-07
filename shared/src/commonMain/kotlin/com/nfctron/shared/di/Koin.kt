package com.nfctron.shared.di

import com.nfctron.shared.api.CoinGeckoApi
import com.nfctron.shared.api.CoinGeckoApiImpl
import com.nfctron.shared.repository.CryptocurrencyRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module

val sharedModule = module {
    single { 
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }
    single<CoinGeckoApi> { CoinGeckoApiImpl(get()) }
    single { CryptocurrencyRepository(get(), get()) }
} 