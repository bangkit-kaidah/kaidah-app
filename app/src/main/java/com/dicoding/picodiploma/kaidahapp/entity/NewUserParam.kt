package com.dicoding.picodiploma.kaidahapp.entity

data class NewUserParam (
    val name: String,
    val email: String,
    val phone: String?,
    val address: String?,
    val info: String?,
    val id: Int
)