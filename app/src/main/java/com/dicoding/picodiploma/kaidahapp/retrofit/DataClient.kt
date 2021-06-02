package com.dicoding.picodiploma.kaidahapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataClient {

    private const val MAIN_URL = "https://shaped-crawler-312912.uc.r.appspot.com/"

    private val retrofitclient = Retrofit.Builder()
        .baseUrl(MAIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val InstanceApi = retrofitclient.create(DataApi::class.java)


}