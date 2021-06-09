package com.dicoding.picodiploma.kaidahapp.api

import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin.FollowResponse
import com.dicoding.picodiploma.kaidahapp.entity.*
import retrofit2.Call
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


    @FormUrlEncoded
    @POST("/api/v1/profile")
    fun userEditProfile(
            @Field("name") name: String,
            @Field("phone") phone: String?,
            @Field("address") address: String?
    ): Call<ProfileResponse>

    @GET("/api/v1/profile")
    fun userProfile(): Call<ProfileResponse>

    @GET("/api/v1/featured-subjects")
    fun topSubject(): Call<ArrayList<FeaturedSubjectsResponse>>

    @GET("/api/v1/documents")
    fun topRegulations(): Call<TopRegulationsResponse>

    @POST("/api/v1/profile/activate")
    fun upToPremium(): Call<PremiumResponse>

    @FormUrlEncoded
    @POST(" /api/v1/sources/users")
    fun userFollow(@Field("source_id")dataId: Int): Call<FollowResponse>
    @DELETE(" /api/v1/sources/users")
    fun deleteUserFollow(@Query("source_id")dataId: Int): Call<FollowResponse>

    @GET(" /api/v1/profile/sources")
    fun userFollowed(): Call<ArrayList<JdhinSerialized>>

    @GET("api/v1/sources")
    fun getDataJdhin(): Call<ArrayList<JdhinSerialized>>

}