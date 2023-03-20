package com.example.kite.notification.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class NotificationResponse(
    @JsonProperty("Notifications_count")
    var notificationsCount: Int? = null,
    @JsonProperty("Notifications_data")
    var notificationsData: List<NotificationsData?>? = null
) {
    data class NotificationsData(
        @JsonProperty("created_at")
        var createdAt: String? = null,
        @JsonProperty("customer_first_name")
        var customerFirstName: String? = null,
        @JsonProperty("customer_id_from")
        var customerIdFrom: Int? = null,
        @JsonProperty("customer_id_to")
        var customerIdTo: Int? = null,
        @JsonProperty("customer_last_name")
        var customerLastName: String? = null,
        @JsonProperty("customer_profile_picture")
        var customerProfilePicture: Any? = null,
        @JsonProperty("description")
        var description: String? = null,
        @JsonProperty("guest_type")
        var guestType: Any? = null,
        @JsonProperty("notification_id")
        var notificationId: Int? = null,
        @JsonProperty("notification_read")
        var notificationRead: Int? = null,
        @JsonProperty("notification_text")
        var notificationText: String? = null,
        @JsonProperty("notification_type")
        var notificationType: Int? = null,
        @JsonProperty("status")
        var status: Int? = null,
        @JsonProperty("third_party_id")
        var thirdPartyId: String? = null,
        @JsonProperty("user_type")
        var userType: Int? = null
    )
}
