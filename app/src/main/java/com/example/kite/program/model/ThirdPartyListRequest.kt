package com.example.kite.program.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ThirdPartyListRequest(
    @JsonProperty("access_token")
    var access_token: String? = "",
    @JsonProperty("user_location")
    var user_location: UserLocation
) {
    data class UserLocation(
        @JsonProperty("lat")
        var lat: String? = "23.0393",
        @JsonProperty("long")
        var long: String? = "72.5306"
    )
}