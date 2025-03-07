package com.nfctron.app

import android.app.Application
import com.nfctron.app.di.appModule
import com.nfctron.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NFCtronApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@NFCtronApp)
            modules(appModule + sharedModule)
        }
    }
} 