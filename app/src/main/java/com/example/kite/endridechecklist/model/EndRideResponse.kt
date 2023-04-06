package com.example.kite.endridechecklist.model


import com.fasterxml.jackson.annotation.JsonProperty

data class EndRideResponse(
    @JsonProperty("amount_refunded")
    var amountRefunded: Int? = null,
    @JsonProperty("booking_id")
    var bookingId: String? = null,
    @JsonProperty("currency_format")
    var currencyFormat: String? = null,
    @JsonProperty("promo_details")
    var promoDetails: PromoDetails? = null,
    @JsonProperty("ride_amount")
    var rideAmount: Int? = null,
    @JsonProperty("total_amount")
    var totalAmount: Int? = null,
    @JsonProperty("wrong_drop_amount")
    var wrongDropAmount: Int? = null
) {
    data class PromoDetails(
        @JsonProperty("promocode")
        var promocode: String? = null,
        @JsonProperty("promocode_id")
        var promocodeId: Int? = null,
        @JsonProperty("promocode_type")
        var promocodeType: Int? = null,
        @JsonProperty("promocode_value")
        var promocodeValue: Int? = null
    )
}
