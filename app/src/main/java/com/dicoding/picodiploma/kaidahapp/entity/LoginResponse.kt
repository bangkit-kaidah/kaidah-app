package com.dicoding.picodiploma.kaidahapp.entity

data class LoginResponse(
    val user: UserParam,
    val token: String,
    val message: String?
)
