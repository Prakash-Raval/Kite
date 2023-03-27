package com.example.kite.paymentsummary.model

import com.google.gson.annotations.SerializedName


data class AddSessionRequest(
    @SerializedName("booking_id")
    val booking_id: String? = "",
    @SerializedName("access_token")
    val access_token: String? = "",
    @SerializedName("battery")
    val battery: String? = "",
    @SerializedName("ride_start_document1")
    val ride_start_document1: String? = "",
    @SerializedName("ride_start_document2")
    val ride_start_document2: String? = "",
    @SerializedName("ride_start_document3")
    val ride_start_document3: String? = "",
    @SerializedName("promocode_id")
    val promocode_id: String? = ""
)
