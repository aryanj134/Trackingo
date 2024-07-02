package com.bitlasoft.trackingo.utils

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.utils.BaseUrl.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceFeedback {

    private val client1 = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client1)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInterface: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}