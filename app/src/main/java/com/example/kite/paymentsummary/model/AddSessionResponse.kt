package com.example.kite.paymentsummary.model


import com.fasterxml.jackson.annotation.JsonProperty

data class AddSessionResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var `data`: Data? = null,
    @JsonProperty("message")
    var message: String? = null
) {
    data class Data(
        @JsonProperty("booking_id")
        var bookingId: String? = null,
        @JsonProperty("currency_format")
        var currencyFormat: String? = null,
        @JsonProperty("mac_address")
        var macAddress: String? = null,
        @JsonProperty("mac_address_lock")
        var macAddressLock: String? = null,
        @JsonProperty("pricing_value")
        var pricingValue: Int? = null,
        @JsonProperty("refundable_deposit")
        var refundableDeposit: Int? = null,
        @JsonProperty("wrong_drop_distance")
        var wrongDropDistance: Int? = null,
        @JsonProperty("wrong_drop_price")
        var wrongDropPrice: Double? = null
    )
}