package com.example.kite.ridedetails.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class RideDetailRequest(
    @get : JsonProperty("access_token")
    var accessToken: String? = null,
    @get : JsonProperty("booking_id")
    var bookingId: String? = null
)