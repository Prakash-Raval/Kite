package com.example.kite.signup.model

data class SignUpRequest(
    val Firstname: String,
    val lastName: String,
    val email: String,
    val mobile: String,
    val country: String,
    val password : String,
    val confirmPassword : String,
)
