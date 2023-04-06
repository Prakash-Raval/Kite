package com.example.kite.changepassword.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChangePasswordRequest(
    @SerializedName("access_token")
    var access_token: String = "",
    @SerializedName("new_password")
    var new_password: String = "",
    @SerializedName("old_password")
    var old_password: String = "",
    @SerializedName("confirm_password")
    var confirm_password: String = ""
)