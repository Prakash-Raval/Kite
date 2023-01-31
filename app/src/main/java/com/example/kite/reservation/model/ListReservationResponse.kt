package com.example.kite.reservation.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ListReservationResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var data: Data? = null,
    @JsonProperty("message")
    var message: String? = null
) {
    data class Data(
        @JsonProperty("count")
        var count: Int? = null,
        @JsonProperty("reservation_data")
        var reservationData: List<Any?>? = null
    )
}