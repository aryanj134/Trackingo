package com.bitlasoft.trackingo.koin.application

import android.app.Application
import com.bitlasoft.trackingo.koin.module.retrofitModule
import com.bitlasoft.trackingo.koin.module.repositoryModule
import com.bitlasoft.trackingo.koin.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(retrofitModule, repositoryModule, viewModelModule)
        }
    }
}