package com.bitlasoft.trackingo.koin.module.appModule

import com.bitlasoft.trackingo.data.ApiInterface
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiModule = module {
    single { provideApiInterface(get()) }
    single(named("feedbackApi")) { provideApiInterface(get(named("feedback"))) }
    single(named("feedbackRatingApi")) { provideApiInterface(get(named("feedbackRating"))) }
}

fun provideApiInterface(retrofit: Retrofit): ApiInterface {
    return retrofit.create(ApiInterface::class.java)
}