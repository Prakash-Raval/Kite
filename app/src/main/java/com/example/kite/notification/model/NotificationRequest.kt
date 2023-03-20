package com.example.kite.notification.model


import com.fasterxml.jackson.annotation.JsonProperty

data class NotificationRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("lang")
    var lang: Int? = null
)