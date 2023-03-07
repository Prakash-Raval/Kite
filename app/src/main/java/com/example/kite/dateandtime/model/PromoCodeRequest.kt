package com.example.kite.dateandtime.model


import com.fasterxml.jackson.annotation.JsonProperty

data class PromoCodeRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("booking_id")
    var booking_id: String? = "0",
    @JsonProperty("promocode")
    var promocode: String? = null
)