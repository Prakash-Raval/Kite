package com.example.kite.bikelisting.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class BikeListingRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("start_date")
    var start_date: String? = null,
    @JsonProperty("start_time")
    var start_time: String? = null,
    @JsonProperty("user_location")
    var user_location: UserLocation? = null,
    @JsonProperty("radius")
    var radius: Long? = 100000000000000000,
    @JsonProperty("third_party_id")
    var third_party_id: String? = "1Gj0mr5eI",
    @JsonProperty("time_zone")
    var time_zone: String? = "Asia/Kolkata"

) {
    data class UserLocation(
        @JsonProperty("lat")
        var lat: String? = "23.024555",
        @JsonProperty("long")
        var long: String? = "72.5668533"
    )
}