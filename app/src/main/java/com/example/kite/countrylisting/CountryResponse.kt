package com.example.kite.countrylisting


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)

data class CountryResponse(
    @JsonProperty("country_list")
    var countryList: List<Country>
) {
    data class Country(
        @JsonProperty("code")
        var code: String? = null,
        @JsonProperty("country")
        var country: String? = null
    )
}