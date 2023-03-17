package com.example.kite.selectpayment.model


import com.fasterxml.jackson.annotation.JsonProperty

data class GetCardRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("customer_id")
    var customer_id: String? = null
)