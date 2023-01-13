package com.example.kite.signup.model


data class SignUpRequest(
    var Firstname: String = "",
    var lastName: String = "",
    var email: String = "",
    var mobile: String = "",
    var country: String = "Canada",
    var password: String = "",
    var confirmPassword: String = "",
    var lang: Int = 0,
    var country_code: Int = +91,
    var device_token: Long = 1234567893,
    var device_type: Int = 2,
    var signup_type: Int = 1
)
