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

    val related: ArrayList<DataHistory>,

    val status: StatusData,
    val subject: SubjectData,
    val source: SourceData,
    @SerializedName("document_type")
    val type: TypeData
)

data class DataHistory(
    val id: Int,
    @SerializedName("judul_dokumen")
    val titleDoc: String,
    @SerializedName("tanggal_penetapan")
    val dateDoc: String
)

data class StatusData(
    val name: String
)


data class TypeData(
    val name: String
)

data class SourceData(
    val name: String
)

data class SubjectData(
    val name: String
)



