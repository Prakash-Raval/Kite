package com.example.kite.subscription.model


import com.fasterxml.jackson.annotation.JsonProperty

data class AddSubRequest(
    @JsonProperty("access_token")
    var access_token: String? = null
)