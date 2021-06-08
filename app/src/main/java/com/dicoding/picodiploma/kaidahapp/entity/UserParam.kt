package com.dicoding.picodiploma.kaidahapp.entity

data class  UserParam(
    var id: Int,
    var name: String,
    var email: String,
    var role_id: Int,
    var phone: String? = null,
    var address: String? = null,
    var info: String? = null
)
