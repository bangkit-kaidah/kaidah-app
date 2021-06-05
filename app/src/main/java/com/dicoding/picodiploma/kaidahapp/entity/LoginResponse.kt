package com.dicoding.picodiploma.kaidahapp.entity

data class LoginResponse(
    val userParam: UserParam,
    val token: String,
    val message: String?
)
