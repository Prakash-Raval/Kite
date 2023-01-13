package com.example.kite.otpvarification.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OtpResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("message")
    var message: String? = null
)