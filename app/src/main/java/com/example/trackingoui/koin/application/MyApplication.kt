package com.example.trackingoui.koin.application

import android.app.Application
import com.example.trackingoui.koin.module.retrofitModule
import com.example.trackingoui.koin.module.repositoryModule
import com.example.trackingoui.koin.module.viewModelModule
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