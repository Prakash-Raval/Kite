package com.example.kite.countrylisting.statelisting


import com.fasterxml.jackson.annotation.JsonProperty

data class StateRequest(
    @JsonProperty("country")
    var country: String? = null
)