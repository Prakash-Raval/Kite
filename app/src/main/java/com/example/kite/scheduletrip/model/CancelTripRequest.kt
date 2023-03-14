package com.example.kite.scheduletrip.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CancelTripRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("cancel_fee")
    var cancel_fee: String? = null,
    @JsonProperty("current_date")
    var current_date: String? = null,
    @JsonProperty("current_time")
    var current_time: String? = null,
    @JsonProperty("forcefully_cancel")
    var forcefully_cancel: String? = null,
    @JsonProperty("reservation_id")
    var reservation_id: String? = null,
    @JsonProperty("time_zone")
    var time_zone: String? = null
)