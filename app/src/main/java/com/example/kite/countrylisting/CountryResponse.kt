package com.example.kite.countrylisting


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*@JsonIgnoreProperties(ignoreUnknown = true)
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
}*/
data class CountryResponse(
    @SerializedName("code")
    @Expose
    var code: String? = null,
    @SerializedName("country")
    @Expose
    var country: String? = null
)
