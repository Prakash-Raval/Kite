package com.example.kite.notification.model


//@JsonIgnoreProperties(ignoreUnknown = false)

data class UpdateNotificationRequest(
    var access_token: String? = null,
    var notification_id: String? = null,
    var is_read: Int
)