package com.dicoding.picodiploma.kaidahapp.api

import android.annotation.SuppressLint
import com.dicoding.picodiploma.kaidahapp.LoginActivity
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://shaped-crawler-312912.uc.r.appspot.com"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method, original.body).addHeader("Accept", "application/json")

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

    private val okHttpClientUser = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method, original.body).addHeader("Accept", "application/json").addHeader("Authorization", "Bearer ${LoginActivity.token}")

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instanceUser: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientUser)
            .build()

        retrofit.create(Api::class.java)
    }
}