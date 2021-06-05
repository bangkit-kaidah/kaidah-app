package com.dicoding.picodiploma.kaidahapp.entity

data class RegisterResponse (
    val userParam: NewUserParam,
    val token: String
)