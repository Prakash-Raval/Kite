package com.example.kite.ridedetails.model


import com.fasterxml.jackson.annotation.JsonProperty

data class PrintReceiptRequest(
    @get : JsonProperty("access_token")
    var accessToken: String? = null,
    @get : JsonProperty("booking_id")
    var bookingId: String? = null,
    @get : JsonProperty("timezone")
    var timezone: String? = "Asia/Kolkata"
)