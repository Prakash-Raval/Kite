package com.example.kite.home.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OnGoingRideRequest(
    @get : JsonProperty("access_token")
    var accessToken: String? = null,
    @get :JsonProperty("customer_id")
    var customerId: Int? = null,
    @JsonProperty("lang")
    var lang: Int? = null
)