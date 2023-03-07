package com.example.kite.dateandtime.model


import com.fasterxml.jackson.annotation.JsonProperty

data class TimeSlotRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("current_time")
    var current_time: String? = null,
    @JsonProperty("geolocation_id")
    var geolocation_id: Int? = 18,
    @JsonProperty("manufacturer_id")
    var manufacturer_id: String? = null,
    @JsonProperty("schedule_date")
    var schedule_date: String? = null,
    @JsonProperty("third_party_id")
    var third_party_id: String? = "i5sejdPiR",
    @JsonProperty("time_zone")
    var time_zone: String? = null,
    @JsonProperty("vehicle_type_id")
    var vehicle_type_id: String? = null
)