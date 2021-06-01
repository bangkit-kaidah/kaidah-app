package com.dicoding.picodiploma.kaidahapp.entity

data class LoginResponse(
    val user: User,
    val token: String,
    val message: String?
)
