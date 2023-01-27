package com.example.kite.changepassword.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChangePasswordResponse(
    @JsonProperty("code")
    var code: String = "",
    @JsonProperty("message")
    var message: String = ""
)
