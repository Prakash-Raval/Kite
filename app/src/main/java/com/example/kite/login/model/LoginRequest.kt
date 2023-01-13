package com.example.kite.login.model

data class LoginRequest(
    var email: String = "",
    var password: String = "",
    var device_type: String = "1",
    var device_token: String = "",
    var app_version: String = "1",
    var lang: Int = 0
)
