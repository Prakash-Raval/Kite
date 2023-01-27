package com.example.kite.forgetpassword.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ForgotPasswordRequest(
    @JsonProperty("email")
    var email: String? = "",
    @JsonProperty("lang")
    var lang: String? = "0"
)