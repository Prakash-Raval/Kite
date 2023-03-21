package com.example.kite.notification.model

import com.google.gson.annotations.SerializedName

//@JsonIgnoreProperties(ignoreUnknown = false)
data class UpdateNotificationRequest(
    @SerializedName("access_token")
    var access_token: String? = null,
    @SerializedName("notification_id")
    var notification_id: String? = null,
    @SerializedName("is_read")
    var is_read : Int = 1
)