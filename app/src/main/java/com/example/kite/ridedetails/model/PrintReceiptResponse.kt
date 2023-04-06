package com.example.kite.ridedetails.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class PrintReceiptResponse(
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("data")
    var data: List<Int>? = null

)
