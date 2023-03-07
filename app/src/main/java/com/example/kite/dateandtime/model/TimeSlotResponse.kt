package com.example.kite.dateandtime.model


import com.fasterxml.jackson.annotation.JsonProperty

data class TimeSlotResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var `data`: Data? = null,
    @JsonProperty("message")
    var message: String? = null
) {
    data class Data(
        @JsonProperty("allTimeSlots")
        var allTimeSlots: List<AllTimeSlot?>? = null
    ) {
        data class AllTimeSlot(
            @JsonProperty("available")
            var available: Boolean? = null,
            @JsonProperty("date")
            var date: String? = null,
            @JsonProperty("time")
            var time: String? = null,
            @JsonProperty("position")
            var position : Int? = null
        )
    }
}