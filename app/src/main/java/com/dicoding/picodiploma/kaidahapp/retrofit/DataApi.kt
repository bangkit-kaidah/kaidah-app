package com.dicoding.picodiploma.kaidahapp.retrofit

import com.dicoding.picodiploma.kaidahapp.datadetail.DetailSerialized
import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.SpecialSerialized
import com.dicoding.picodiploma.kaidahapp.datasubject.SubjectSerialized
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface DataApi {
    @GET("api/v1/subjects")
    fun getDataSubject(): Call<ArrayList<SubjectSerialized>>

    @GET("api/v1/documents/{id}")
    fun getDetail(@Path("id") dataID: Int): Call<List<DetailSerialized>>

    @GET("api/v1/documents")
    fun getDataSpecial(@Query("subject") dataID: Int): Call<SpecialSerialized>

    @GET("api/v1/documents")
    fun getDataAll(): Call<SpecialSerialized>

    @GET("api/v1/documents")
    fun getDataRegulationSecondary(
        @Query("subject") dataID: Int,
        @Query("page") data: Int
    ): Call<SpecialSerialized>

    @GET("api/v1/documents")
    fun getDataSearchSecondary(
        @Query("search") dataID: String,
        @Query("page") data: Int
    ): Call<SpecialSerialized>

    @GET("api/v1/documents")
    fun getDataJDHINSecondary(
        @Query("source") dataID: Int,
        @Query("page") data: Int
    ): Call<SpecialSerialized>

    //query 1 sesuai dengan EXTRA di PageRegulationActivity
    //query 2 sesuai dengan 0 + a lalu val a = 1++

    @GET("api/v1/sources")
    fun getDataJdhin(): Call<ArrayList<JdhinSerialized>>

    @GET("api/v1/documents")
    fun getRegulationJdhin(@Query("source") dataID: Int): Call<SpecialSerialized>

    @GET("api/v1/subjects")
    fun getSearchSubject(@Query("search") dataInput: String): Call<ArrayList<SubjectSerialized>>

    @GET("api/v1/sources")
    fun getSearchJdhin(@Query("search") dataInput: String): Call<ArrayList<JdhinSerialized>>

    @GET("api/v1/documents")
    fun getSearchDocument(@Query("search") dataInput: String): Call<SpecialSerialized>

    //getDataHistory
    @GET("api/v1/documents/{id}")
    fun getDataHistory(@Path("id") dataID: Int): Call<List<DetailSerialized>>




}