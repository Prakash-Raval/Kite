package com.example.kite.countrylisting.statelisting


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class StateResponse(
    @JsonProperty("code")
    var code: String? = null,
    @JsonProperty("country")
    var country: String? = null,
    @JsonProperty("states")
    var states: List<String?>? = null
)
