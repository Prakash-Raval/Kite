package com.example.kite.scanqr.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ScanQRRequest(
    @JsonProperty("access_token")
    var access_token: String? = "",
    @JsonProperty("geolocation_id")
    var geolocation_id: Int? = 18,
    @JsonProperty("pickup_lat")
    var pickup_lat: Double? = 22.9927389,
    @JsonProperty("pickup_long")
    var pickup_long: Double? = 72.6301746,
    @JsonProperty("qr_code")
    var qr_code: String? = "",
    @JsonProperty("ride_country")
    var ride_country: String? = "United states",
    @JsonProperty("timezone")
    var timezone: String? = "IST",
    @JsonProperty("vehicle_type_slug")
    var vehicle_type_slug: String? = ""
)