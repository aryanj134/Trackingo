package com.example.trackingoui.koin.module

import com.example.trackingoui.data.APIInterface
import com.example.trackingoui.utils.RetrofitInstance
import org.koin.dsl.module

val retrofitModule = module {
    single { RetrofitInstance.getRetrofitInstance().create(APIInterface::class.java) }
}
