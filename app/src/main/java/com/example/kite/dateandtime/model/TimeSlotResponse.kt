package com.example.kite.dateandtime.model

import com.google.gson.annotations.SerializedName

data class TimeSlotResponse(
    @SerializedName("allTimeSlots")
    var allTimeSlots: List<AllTimeSlot?>? = null
) {
    data class AllTimeSlot(
        @SerializedName("available")
        var available: Boolean? = null,
        @SerializedName("date")
        var date: String? = null,
        @SerializedName("time")
        var time: String? = "",
        @SerializedName("position")
        var position: Int? = null,
        var isItemSelected: Boolean = false,
        var convertedTime: String? = ""
    )
}
