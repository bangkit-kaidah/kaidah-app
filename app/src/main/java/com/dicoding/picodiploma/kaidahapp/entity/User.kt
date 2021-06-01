package com.dicoding.picodiploma.kaidahapp.entity

data class  User(
    val id: Int,
    val name: String,
    val email: String,
    val role_id: Int,
    val phone: Int,
    val address: String,
    val info: String
)
