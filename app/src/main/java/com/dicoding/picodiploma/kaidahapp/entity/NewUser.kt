package com.dicoding.picodiploma.kaidahapp.entity

data class NewUser (
    val name: String,
    val email: String,
    val phone: Int?,
    val address: String?,
    val info: String?,
    val id: Int
)