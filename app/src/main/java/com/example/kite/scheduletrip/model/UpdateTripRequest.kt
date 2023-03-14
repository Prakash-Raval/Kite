package com.example.kite.scheduletrip.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateTripRequest(
    @get: JsonProperty("access_token")
    @param: JsonProperty("access_token")
    var accessToken: String? = null,
    @JsonProperty("duration")
    var duration: String? = null,
    @get: JsonProperty("forcefully_update")
    @param: JsonProperty("forcefully_update")
    var forcefullyUpdate: Int? = null,
    @get: JsonProperty("geolocation_id")
    @param: JsonProperty("geolocation_id")
    var geolocationId: String? = "18",
    @get: JsonProperty("promocode_id")
    @param: JsonProperty("promocode_id")
    var promocodeId: String? = "",
    @get: JsonProperty("reservation_customer_id")
    @param: JsonProperty("reservation_customer_id")
    var reservationCustomerId: String? = null,
    @get: JsonProperty("reservation_id")
    @param: JsonProperty("reservation_id")
    var reservationId: String? = null,
    @get: JsonProperty("start_date")
    @param: JsonProperty("start_date")
    var startDate: String? = null,
    @get: JsonProperty("start_time")
    @param: JsonProperty("start_time")
    var startTime: String? = null,
    @get: JsonProperty("time_zone")
    @param: JsonProperty("time_zone")
    var timeZone: String? = null
)
