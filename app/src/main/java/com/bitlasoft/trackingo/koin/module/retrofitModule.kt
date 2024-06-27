package com.bitlasoft.trackingo.koin.module

import com.bitlasoft.trackingo.data.APIInterface
import com.bitlasoft.trackingo.utils.RetrofitInstance
import org.koin.dsl.module

val retrofitModule = module {
    single { RetrofitInstance.getRetrofitInstance().create(APIInterface::class.java) }
}
