package com.example.kite.scheduletrip.model


import com.fasterxml.jackson.annotation.JsonProperty

data class TripRequest(
    @get: JsonProperty("access_token")
    @param: JsonProperty("access_token")
    var accessToken: String? = null,
    @get: JsonProperty("duration")
    @param: JsonProperty("duration")
    var duration: String? = null,
    @get: JsonProperty("is_subscribed")
    @param: JsonProperty("is_subscribed")
    var isSubscribed: String? = null,
    @get: JsonProperty("manufacturer_id")
    @param: JsonProperty("manufacturer_id")
    var manufacturerId: String? = null,
    @get: JsonProperty("promocode_id")
    @param: JsonProperty("promocode_id")
    var promocodeId: String? = null,
    @get: JsonProperty("recurring_day_count")
    @param: JsonProperty("recurring_day_count")
    var recurringDayCount: String? = null,
    @get: JsonProperty("reservation_customer_id")
    @param: JsonProperty("reservation_customer_id")
    var reservationCustomerId: Int? = null,
    @get: JsonProperty("start_date")
    @param: JsonProperty("start_date")
    var startDate: String? = null,
    @get: JsonProperty("start_time")
    @param: JsonProperty("start_time")
    var startTime: String? = null,
    @get: JsonProperty("time_zone")
    @param: JsonProperty("time_zone")
    var timeZone: String? = null,
    @get: JsonProperty("vehicle_reservation_pricing_id")
    @param: JsonProperty("vehicle_reservation_pricing_id")
    var vehicleReservationPricingId: Int? = null,
    @get: JsonProperty("vehicle_type_id")
    @param: JsonProperty("vehicle_type_id")
    var vehicleTypeId: String? = null,
    @get: JsonProperty("third_party_id")
    @param: JsonProperty("third_party_id")
    var thirdPartyId: String? = "",
    @get: JsonProperty("geolocation_id")
    @param: JsonProperty("geolocation_id")
    var geolocationId: String? = "18"
)