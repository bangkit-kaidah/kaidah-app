package com.dicoding.picodiploma.kaidahapp.retrofit

import com.dicoding.picodiploma.kaidahapp.datadetail.DetailSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.SpecialSerialized
import com.dicoding.picodiploma.kaidahapp.datasubject.SubjectSerialized
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataApi {
    @GET("api/v1/subjects")
    fun getDataSubject(): Call<ArrayList<SubjectSerialized>>


    @GET("api/v1/documents/{id}")
    fun getDetail(@Path("id") dataID: Int): Call<List<DetailSerialized>>

    @GET("api/v1/documents")
    fun getDataSpecial(@Query("subject")dataID: Int): Call<SpecialSerialized>

    //fun getDataSpecial(@Query("subject")dataID: Int): Call<SpecialSerialized>
    //masukkan data di SubjectSerialized seperti title dan masa berlaku
    //pastikan dulu bahwa getDataSubject dapat berjalan semestinya


}