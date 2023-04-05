package com.example.kite.scheduletrip.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ScheduleTripRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("vehicle_type_id")
    var vehicle_type_id: String? = null,
    @JsonProperty("manufacturer_id")
    var manufacturer_id: String? = null,
    @JsonProperty("third_party_id")
    var third_party_id: String? = "",

    )