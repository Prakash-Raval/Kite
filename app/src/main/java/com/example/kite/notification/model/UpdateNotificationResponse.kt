package com.example.kite.notification.model


import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateNotificationResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("message")
    var message: String? = null
)