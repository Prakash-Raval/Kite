package com.example.kite.notification.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UpdateNotificationRequest(
    @SerializedName("access_token")
    var access_token: String? = null,
    @SerializedName("notification_id")
    var notification_id: String? = null,
    @get: JsonProperty("is_read")
//  @param: JsonProperty("is_read")
    var is_read: String? = null,

)
