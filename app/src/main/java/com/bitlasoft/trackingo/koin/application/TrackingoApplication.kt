package com.bitlasoft.trackingo.koin.application

import android.app.Application
import com.bitlasoft.trackingo.koin.module.appModule.ApiModule
import com.bitlasoft.trackingo.koin.module.networkModule.NetworkModule
import com.bitlasoft.trackingo.koin.module.appModule.RepositoryModule
import com.bitlasoft.trackingo.koin.module.appModule.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TrackingoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TrackingoApplication)
            modules(NetworkModule, ApiModule, RepositoryModule, viewModule)
        }
    }
}