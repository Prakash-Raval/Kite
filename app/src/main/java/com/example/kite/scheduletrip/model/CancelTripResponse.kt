package com.example.kite.scheduletrip.model


import com.fasterxml.jackson.annotation.JsonProperty

data class CancelTripResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("message")
    var message: String? = null
)