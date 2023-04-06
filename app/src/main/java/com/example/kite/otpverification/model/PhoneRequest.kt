package com.example.kite.otpverification.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhoneRequest(
    @get:JsonProperty("access_token")
    var accessToken: String? = "",
    @get:JsonProperty("country_code")
    var countryCode: String? = "+91",
    @get:JsonProperty("lang")
    var lang: String? = "0",
    @get:JsonProperty("otp_code")
    var otpCode: String? = "",
    @get:JsonProperty("phone_number")
    var phoneNumber: String? = ""
)