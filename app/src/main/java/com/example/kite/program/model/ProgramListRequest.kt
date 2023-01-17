package com.example.kite.program.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ProgramListRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("user_location")
    var user_location: UserLocation? = null
) {
    data class UserLocation(
        @JsonProperty("lat")
        var lat: String? = null,
        @JsonProperty("long")
        var long: String? = null
    )
}