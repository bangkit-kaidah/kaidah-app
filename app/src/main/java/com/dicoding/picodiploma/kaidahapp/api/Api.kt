package com.dicoding.picodiploma.kaidahapp.api

import retrofit2.Call
import com.dicoding.picodiploma.kaidahapp.entity.LoginResponse
import com.dicoding.picodiploma.kaidahapp.entity.ProfileResponse
import com.dicoding.picodiploma.kaidahapp.entity.RegisterResponse
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("/api/v1/login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/api/v1/register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("phone") phone: String?,
        @Field("address") address: String?,
        @Field("info") info: String?,
    ): Call<RegisterResponse>

    @GET("/api/v1/profile")
    fun userProfile(): Call<ProfileResponse>

}