package com.dicoding.picodiploma.kaidahapp.entity

data class RegisterResponse (
    val user: NewUser,
    val token: String
)