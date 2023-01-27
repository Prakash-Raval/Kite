package com.example.kite.forgetpassword.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ForgetPasswordResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("message")
    var message: String? = null
)