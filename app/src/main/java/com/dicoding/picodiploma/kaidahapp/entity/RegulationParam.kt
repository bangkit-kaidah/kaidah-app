package com.dicoding.picodiploma.kaidahapp.entity

class RegulationParam (
    val id : Int,
    val judul_dokumen: String,
    val status: DataId,
    val subject: DataSubject
)

data class DataId(
    val id: Int,
    val name: String
)

data class DataSubject(
    val id: Int,
    val name: String
)