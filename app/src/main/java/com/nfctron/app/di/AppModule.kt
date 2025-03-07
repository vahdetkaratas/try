package com.nfctron.app.di

import com.nfctron.app.ui.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CryptoViewModel(get()) }
} 