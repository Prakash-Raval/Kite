package com.example.kite.countrylisting.statelisting


import com.fasterxml.jackson.annotation.JsonProperty

data class StateResponse(
    @JsonProperty("state_list")
    var stateList: List<State?>? = null
) {
    data class State(
        @JsonProperty("code")
        var code: String? = null,
        @JsonProperty("country")
        var country: String? = null,
        @JsonProperty("states")
        var states: List<String?>? = null
    )
}