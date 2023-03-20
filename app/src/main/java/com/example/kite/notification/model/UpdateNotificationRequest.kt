package com.example.kite.notification.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpdateNotificationRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("is_read")
    var is_read: Int? = null,
    @JsonProperty("notification_id")
    var notification_id: String? = null
)