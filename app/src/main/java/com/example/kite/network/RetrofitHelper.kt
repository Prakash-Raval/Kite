package com.example.kite.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitHelper {
    private val mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .build()

    fun getInstance(url :String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(mOkHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}