package com.bitlasoft.trackingo.koin.module.networkModule

import com.bitlasoft.trackingo.utils.BaseUrl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val NetworkModule = module {
    factory { provideGson() }
    factory { provideOkHttpClient() }
    single { provideRetrofit(get(), get(), get()) }
    single(named("feedback")) { provideRetrofit(BaseUrl.FEEDBACK, get(), get()) }
    single(named("feedbackRating")) { provideRetrofit(BaseUrl.FEEDBACK_RATING, get(), get()) }
}

fun provideGson(): Gson {
    return GsonBuilder()
        .setLenient()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}

fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(120, TimeUnit.SECONDS)
        .connectTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()
} else {
    OkHttpClient.Builder()
        .readTimeout(120, TimeUnit.SECONDS)
        .connectTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(baseUrl: String, factory: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .build()
}

