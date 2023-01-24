package com.example.kite.addcard.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AddCardResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var `data`: Data? = null,
    @JsonProperty("message")
    var message: String? = null
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Data(
        @JsonProperty("cardName")
        var cardName: String? = null,
        @JsonProperty("cardNumber")
        var cardNumber: String? = null
    )
}