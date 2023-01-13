package com.example.kite.otpvarification.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class OtpRequest(
    @JsonProperty("access_token")
    var accessToken: String? = null,
    @JsonProperty("lang")
    var lang: String? = null,
    @JsonProperty("otp_code")
    var otpCode: Int? = null
)