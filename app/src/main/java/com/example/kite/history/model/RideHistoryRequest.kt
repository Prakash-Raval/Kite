package com.example.kite.history.model


import com.fasterxml.jackson.annotation.JsonProperty

data class RideHistoryRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("customer_id")
    var customer_id: Int? = null,
    @JsonProperty("limit")
    var limit: Int? = 10,
    @JsonProperty("skip")
    var skip: Int? = 0
)