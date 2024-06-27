package com.bitlasoft.trackingo.koin.application

import android.app.Application
import com.bitlasoft.trackingo.koin.module.appModule.ApiModule
import com.bitlasoft.trackingo.koin.module.networkModule.NetworkModule
import com.bitlasoft.trackingo.koin.module.appModule.RepositoryModule
import com.bitlasoft.trackingo.koin.module.appModule.viewModule
import com.bitlasoft.trackingo.utils.sharedPref.PreferenceUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TrackingoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TrackingoApplication)
            modules(listOf(RepositoryModule, viewModule, NetworkModule, ApiModule))
        }

        PreferenceUtil.init(this)
    }
}