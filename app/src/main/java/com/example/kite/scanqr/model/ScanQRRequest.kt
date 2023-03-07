package com.example.kite.scanqr.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ScanQRRequest(
    @JsonProperty("access_token")
    var accessToken: String? = null,
    @JsonProperty("geolocation_id")
    var geolocationId: Int? = null,
    @JsonProperty("pickup_lat")
    var pickupLat: Double? = null,
    @JsonProperty("pickup_long")
    var pickupLong: Double? = null,
    @JsonProperty("qr_code")
    var qrCode: String? = null,
    @JsonProperty("ride_country")
    var rideCountry: String? = null,
    @JsonProperty("timezone")
    var timezone: String? = null,
    @JsonProperty("vehicle_type_slug")
    var vehicleTypeSlug: String? = null
)