package com.example.kite.addcard.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AddCardRequest(
    @JsonProperty("access_token")
    var access_token: String? = null,
    @JsonProperty("card_name")
    var card_name: String? = null,
    @JsonProperty("card_number")
    var card_number: String? = null,
    @JsonProperty("card_token")
    var card_token: String? = null,
    @JsonProperty("card_type")
    var card_type: String? = null,
    @JsonProperty("customer_id")
    var customer_id: String? = null,
    @JsonProperty("expiry_date")
    var expiry_date: String? = null,
    @JsonProperty("is_default")
    var is_default: Int? = null,
    @JsonProperty("stripe_card_id")
    var stripe_card_id: String? = null
)