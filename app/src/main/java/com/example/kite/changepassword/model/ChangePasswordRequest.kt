package com.example.kite.changepassword.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChangePasswordRequest(
    @JsonProperty("access_token")
    var access_token: String = "",
    @JsonProperty("new_password")
    var new_password: String = "",
    @JsonProperty("old_password")
    var old_password: String = "",

)