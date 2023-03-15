package com.example.kite.addcard.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AddCardResponse(
    @JsonProperty("cardName")
    var cardName: String? = null,
    @JsonProperty("cardNumber")
    var cardNumber: String? = null
)
