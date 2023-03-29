package com.example.kite.paymentsummary.model


import com.fasterxml.jackson.annotation.JsonProperty

data class AddSessionResponse(
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