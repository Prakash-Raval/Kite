package com.example.kite.selectpayment.model


import com.fasterxml.jackson.annotation.JsonProperty

data class GetCardResponse(
    @JsonProperty("details")
    var details: List<Detail?>? = null
) {
    data class Detail(
        @JsonProperty("card_id")
        var cardId: Int? = null,
        @JsonProperty("card_name")
        var cardName: String? = null,
        @JsonProperty("card_number")
        var cardNumber: String? = null,
        @JsonProperty("card_token")
        var cardToken: String? = null,
        @JsonProperty("card_type")
        var cardType: String? = null,
        @JsonProperty("expiry_date")
        var expiryDate: String? = null,
        @JsonProperty("expiry_month")
        var expiryMonth: String? = null,
        @JsonProperty("expiry_Year")
        var expiryYear: String? = null,
        @JsonProperty("is_default")
        var isDefault: Int? = null,
        @JsonProperty("stripe_card_id")
        var stripeCardId: String? = null
    )
}
