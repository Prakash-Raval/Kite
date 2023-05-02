package com.example.kite.base.network.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
open class ResponseData<T> {
    @SerializedName("status_code")
    @Expose
    var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: T? = null

    @get : JsonProperty("country_list")
    var countryList: List<T>? = null



    override fun toString(): String {
        return "com.example.kite.base.network.model.ResponseWrapper{" + "data=" + data.toString()
    }

}
