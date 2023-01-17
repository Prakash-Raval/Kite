package com.example.kite.otpverification.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(value = ["accessToken", "otpCode"], ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
data class OtpRequest(
    @JsonProperty("lang")
    var lang: String? = "0",
    @get: JsonProperty("access_token")
    @param: JsonProperty("access_token")
//    @JsonProperty("access_token")
    var accessToken: String? = "",
    @get:JsonProperty("otp_code")
    @param:JsonProperty("otp_code")
//    @JsonProperty("otp_code")
    var otpCode: String? = ""
)


/*@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class OtpRequest(
    @JsonProperty("lang")
    var lang: String? = "0",
    @JsonProperty("access_token")
    var accessToken: String? = "",
    @JsonProperty("otp_code")
    var otpCode: String? = ""
)*/

/*
data class OtpRequest(
    var lang: String? = "0",
    var access_token: String? = "",
    var otp_code: String? = ""
)*/
