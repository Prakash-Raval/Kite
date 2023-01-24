package com.example.kite.addcard.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AddCardRequest(
    @JsonProperty("access_token")
    var accessToken: String? = null,
    @JsonProperty("card_name")
    var cardName: String? = null,
    @JsonProperty("card_number")
    var cardNumber: String? = null,
    @JsonProperty("card_token")
    var cardToken: String? = null,
    @JsonProperty("card_type")
    var cardType: String? = null,
    @JsonProperty("customer_id")
    var customerId: String? = null,
    @JsonProperty("expiry_date")
    var expiryDate: String? = null,
    @JsonProperty("is_default")
    var isDefault: Int? = null,
    @JsonProperty("stripe_card_id")
    var stripeCardId: String? = null
)