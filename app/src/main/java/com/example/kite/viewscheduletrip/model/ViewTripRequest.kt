package com.example.kite.viewscheduletrip.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ViewTripRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("reservation_id")
    var reservation_id: String? = null
)