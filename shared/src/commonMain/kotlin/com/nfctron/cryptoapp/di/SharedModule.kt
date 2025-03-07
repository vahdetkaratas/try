import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClient() }
    single<CoinGeckoApi> { CoinGeckoApiImpl(get()) }
    single { createDatabase(get()) }
    single { CryptoRepository(get(), get()) }
}

expect fun createDatabase(context: Any): CryptoDatabase
expect fun createHttpClient(): HttpClient 