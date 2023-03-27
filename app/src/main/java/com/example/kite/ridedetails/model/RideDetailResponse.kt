package com.example.kite.ridedetails.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RideDetailResponse(
    @JsonProperty("address")
    var address: String? = null,
    @JsonProperty("amount_refunded")
    var amountRefunded: String? = null,
    @JsonProperty("booking_id")
    var bookingId: String? = null,
    @JsonProperty("card_id")
    var cardId: String? = null,
    @JsonProperty("card_number")
    var cardNumber: String? = null,
    @JsonProperty("card_type")
    var cardType: String? = null,
    @JsonProperty("company_name")
    var companyName: String? = null,
    @JsonProperty("currency_format")
    var currencyFormat: String? = null,
    @JsonProperty("customer_first_name")
    var customerFirstName: String? = null,
    @JsonProperty("customer_last_name")
    var customerLastName: String? = null,
    @JsonProperty("dest_address")
    var destAddress: String? = null,
    @JsonProperty("distance")
    var distance: Double? = null,
    @JsonProperty("duration")
    var duration: Double? = null,
    @JsonProperty("end_address")
    var endAddress: String? = null,
    @JsonProperty("end_date")
    var endDate: String? = null,
    @JsonProperty("end_geolocation_id")
    var endGeolocationId: Int? = null,
    @JsonProperty("end_location")
    var endLocation: EndLocation? = null,
    @JsonProperty("geolocation_id")
    var geolocationId: Int? = null,
    @JsonProperty("overall_path")
    var overallPath: List<OverallPath?>? = null,
    @JsonProperty("pickup_location")
    var pickupLocation: PickupLocation? = null,
    @JsonProperty("pricing_value")
    var pricingValue: String? = null,
    @JsonProperty("promo_details")
    var promoDetails: PromoDetails? = null,
    @JsonProperty("promocode_id")
    var promocodeId: String? = null,
    @JsonProperty("promocode_type")
    var promocodeType: String? = null,
    @JsonProperty("promocode_value")
    var promocodeValue: String? = null,
    @JsonProperty("refundable_deposit")
    var refundableDeposit: String? = null,
    @JsonProperty("returnable_amount")
    var returnableAmount: String? = null,
    @JsonProperty("ride_amount")
    var rideAmount: String? = null,
    @JsonProperty("ride_country")
    var rideCountry: String? = null,
    @JsonProperty("start_date")
    var startDate: String? = null,
    @JsonProperty("status")
    var status: Int? = null,
    @JsonProperty("total_amount")
    var totalAmount: String? = null,
    @JsonProperty("total_distance")
    var totalDistance: Double? = null,
    @JsonProperty("transaction_id")
    var transactionId: String? = null,
    @JsonProperty("travel_charge")
    var travelCharge: String? = null,
    @JsonProperty("travelcharge")
    var travelcharge: String? = null,
    @JsonProperty("vehicle_type")
    var vehicleType: String? = null,
    @JsonProperty("vehicle_type_image")
    var vehicleTypeImage: String? = null,
    @JsonProperty("wrong_drop_amount")
    var wrongDropAmount: String? = null,
    @JsonProperty("wrong_drop_distance")
    var wrongDropDistance: String? = null,
    @JsonProperty("wrong_drop_price")
    var wrongDropPrice: Double? = null
) {
    data class EndLocation(
        @JsonProperty("x")
        var x: Double? = null,
        @JsonProperty("y")
        var y: Double? = null
    )

    data class OverallPath(
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

    data class PromoDetails(
        @JsonProperty("promocode")
        var promocode: String? = null,
        @JsonProperty("promocode_id")
        var promocodeId: Int? = null,
        @JsonProperty("promocode_type")
        var promocodeType: Int? = null,
        @JsonProperty("promocode_value")
        var promocodeValue: String? = null
    )
}
