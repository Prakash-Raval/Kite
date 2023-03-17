package com.example.kite.history.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RideHistoryResponse(
    @JsonProperty("customer_data")
    var customerData: CustomerData? = null,
    @JsonProperty("ride_history")
    var rideHistory: List<RideHistory?>? = null,
    @JsonProperty("total_rides")
    var totalRides: Int? = null
) {
    data class CustomerData(
        @JsonProperty("country")
        var country: String? = null,
        @JsonProperty("country_code")
        var countryCode: String? = null
    )

    data class RideHistory(
        @JsonProperty("booking_id")
        var bookingId: String? = null,
        @JsonProperty("company_name")
        var companyName: String? = null,
        @JsonProperty("continue_ride_type")
        var continueRideType: Int? = null,
        @JsonProperty("country")
        var country: String? = null,
        @JsonProperty("country_code")
        var countryCode: String? = null,
        @JsonProperty("currency_format")
        var currencyFormat: String? = null,
        @JsonProperty("dest_address")
        var destAddress: String? = null,
        @JsonProperty("distance")
        var distance: Double? = null,
        @JsonProperty("end_location")
        var endLocation: EndLocation? = null,
        @JsonProperty("first_name")
        var firstName: String? = null,
        @JsonProperty("is_continue_ride")
        var isContinueRide: Int? = null,
        @JsonProperty("last_name")
        var lastName: String? = null,
        @JsonProperty("modal_name")
        var modalName: String? = null,
        @JsonProperty("order_by_value")
        var orderByValue: String? = null,
        @JsonProperty("pickup_location")
        var pickupLocation: PickupLocation? = null,
        @JsonProperty("qr_code")
        var qrCode: String? = null,
        @JsonProperty("rating")
        var rating: String? = null,
        @JsonProperty("reservation_id")
        var reservationId: String? = null,
        @JsonProperty("ride_country")
        var rideCountry: String? = null,
        @JsonProperty("start_date")
        var startDate: String? = null,
        @JsonProperty("status")
        var status: Int? = null,
        @JsonProperty("total_amount")
        var totalAmount: Double? = null,
        @JsonProperty("total_distance")
        var totalDistance: Double? = null,
        @JsonProperty("vehicle_type")
        var vehicleType: String? = null,
        @JsonProperty("vehicle_type_image")
        var vehicleTypeImage: String? = null,
        @JsonProperty("zone_id")
        var zoneId: String? = null
    ) {
        data class EndLocation(
            @JsonProperty("x")
            var x: Double? = null,
            @JsonProperty("y")
            var y: Double? = null
        )
        data class PickupLocation(
            @JsonProperty("x")
            var x: Double? = null,
            @JsonProperty("y")
            var y: Double? = null
        )
    }
}
