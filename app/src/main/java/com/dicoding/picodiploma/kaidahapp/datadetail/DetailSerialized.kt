package com.dicoding.picodiploma.kaidahapp.datadetail

import com.google.gson.annotations.SerializedName

data class DetailSerialized(
    @SerializedName("id")
    val id: Int,
    @SerializedName("judul_dokumen")
    val titleDocument: String,
    @SerializedName("nomor_peraturan")
    val numberRegulation: String,
    @SerializedName("tempat_penetapan")
    val placeDetermination: String,
    @SerializedName("tanggal_penetapan")
    val dateDetermination: String,
    @SerializedName("tanggal_pengundangan")
    val dateRelease: String,
    @SerializedName("bidang_hukum")
    val kindRegulation: String,
    @SerializedName("file_url")
    val sourceDetail: String,
    val link: String,
    val status: StatusData,
    @SerializedName("document_type")
    val type: TypeData
)

data class StatusData(
    val name: String
)


data class TypeData(
    val name: String
)



