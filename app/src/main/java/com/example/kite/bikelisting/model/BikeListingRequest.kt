package com.example.kite.bikelisting.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class BikeListingRequest(
    @SerializedName("access_token")
    var access_token: String? = null,
    @SerializedName("start_date")
    var start_date: String? = null,
    @SerializedName("start_time")
    var start_time: String? = null,
    @SerializedName("user_location")
    var user_location: UserLocation? = null,
    @SerializedName("radius")
    var radius: Long? = 100000000000000000,
    @SerializedName("third_party_id")
    var third_party_id: String? = "i5sejdPiR",
    @SerializedName("time_zone")
    var time_zone: String? = "Asia/Kolkata"


) {
    data class UserLocation(
        @SerializedName("lat")
        var lat: String? = "23.024555",
        @SerializedName("long")
        var long: String? = "72.5668533"
    )
}