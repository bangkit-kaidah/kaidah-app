package com.dicoding.picodiploma.kaidahapp.entity

data class RegulationParam (
    var id : Int? = 0,
    var judul_dokumen: String? = null,
    var status: DataId? = null,
    var subject: DataSubject? = null
)

data class DataId(
    val id: Int? = 0,
    val name: String? = null
)

data class DataSubject(
    val id: Int? = 0,
    val name: String? = null
)