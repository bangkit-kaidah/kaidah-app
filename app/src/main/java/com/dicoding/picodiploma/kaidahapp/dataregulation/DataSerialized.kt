package com.dicoding.picodiploma.kaidahapp.dataregulation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataSerialized(

    val id : Int,
    @SerializedName("judul_dokumen")
    val title: String,

    @SerializedName("nomor_peraturan")
    val numberRegulation: String,

    @SerializedName("status")
    val status: DataId,

    val subject: DataSubject
)


data class DataId(
    val id: Int,
    var name: String
)

data class DataSubject(
    val id: Int,
    val name: String
)
