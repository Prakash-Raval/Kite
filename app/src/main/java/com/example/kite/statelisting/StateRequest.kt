package com.example.kite.statelisting


import com.fasterxml.jackson.annotation.JsonProperty

data class StateRequest(
    @JsonProperty("country")
    var country: String? = null
)