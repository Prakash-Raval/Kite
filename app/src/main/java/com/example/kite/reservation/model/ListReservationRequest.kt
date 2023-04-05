package com.example.kite.reservation.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ListReservationRequest(
    @JsonProperty("access_token")
    var access_token: String? = "",
    @JsonProperty("current_date")
    var current_date: String? = "",
    @JsonProperty("current_time")
    var current_time: String? = "",
    @JsonProperty("start_date")
    var start_date: String? = "",
    @JsonProperty("start_time")
    var start_time: String? = "",
    @JsonProperty("third_party_id")
    var third_party_id: String? = "",
    @JsonProperty("time_zone")
    var time_zone: String? = "Asia/Kolkata",
    @JsonProperty("limit")
    var limit: Int? = 200,
    @JsonProperty("offset")
    var offset: Int? = 0
)