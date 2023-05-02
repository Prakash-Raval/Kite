package com.example.kite.base.network.model

import com.example.kite.countrylisting.CountryResponse
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
class ResponseListData<T> {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status_code")
    @Expose
    var code: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<T>? = null

    @SerializedName("country_list")
    @Expose
    var countryList: List<CountryResponse>? = null

    override fun toString(): String {
        return "com.example.kite.base.network.model.ResponseWrapper{" +
                "data=" + data.toString()
    }
}
