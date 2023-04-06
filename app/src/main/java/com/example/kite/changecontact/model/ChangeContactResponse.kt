package com.example.kite.changecontact.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChangeContactResponse(
    @get:JsonProperty("otp_code")
    var otpCode: Int? = null
)
