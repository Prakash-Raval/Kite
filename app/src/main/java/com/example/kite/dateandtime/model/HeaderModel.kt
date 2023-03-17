package com.example.kite.dateandtime.model

data class HeaderModel(
    var header: String = "",
    var list: List<TimeSlotResponse.AllTimeSlot>? = null,
    var isVisible: Boolean? = null

)
