package com.example.kite.home.model


import com.fasterxml.jackson.annotation.JsonProperty

data class OnGoingRideResponse(
    @JsonProperty("booking_id")
    var bookingId: Int? = null,
    @JsonProperty("category_id")
    var categoryId: Int? = null,
    @JsonProperty("currency_format")
    var currencyFormat: String? = null,
    @JsonProperty("end_trip_image")
    var endTripImage: String? = null,
    @JsonProperty("geolocation_id")
    var geolocationId: Int? = null,
    @JsonProperty("is_lock_active")
    var isLockActive: Int? = null,
    @JsonProperty("mac_address")
    var macAddress: String? = null,
    @JsonProperty("mac_address_cycle")
    var macAddressCycle: String? = null,
    @JsonProperty("mac_address_lock")
    var macAddressLock: String? = null,
    @JsonProperty("pickup_location")
    var pickupLocation: PickupLocation? = null,
    @JsonProperty("pricing_id")
    var pricingId: Int? = null,
    @JsonProperty("pricing_value")
    var pricingValue: Int? = null,
    @JsonProperty("qr_code")
    var qrCode: String? = null,
    @JsonProperty("rating_image")
    var ratingImage: String? = null,
    @JsonProperty("ride_country")
    var rideCountry: String? = null,
    @JsonProperty("ride_duration_time")
    var rideDurationTime: String? = null,
    @JsonProperty("start_date")
    var startDate: String? = null,
    @JsonProperty("third_party_id")
    var thirdPartyId: String? = null,
    @JsonProperty("vehicle_type")
    var vehicleType: String? = null,
    @JsonProperty("vehicle_type_image")
    var vehicleTypeImage: String? = null,
    @JsonProperty("vehicle_type_slug")
    var vehicleTypeSlug: String? = null,
    @JsonProperty("wrong_drop_distance")
    var wrongDropDistance: Int? = null,
    @JsonProperty("wrong_drop_image")
    var wrongDropImage: String? = null,
    @JsonProperty("wrong_drop_price")
    var wrongDropPrice: Double? = null
) {
    data class PickupLocation(
        @JsonProperty("x")
        var x: Double? = null,
        @JsonProperty("y")
        var y: Double? = null
    )
}
