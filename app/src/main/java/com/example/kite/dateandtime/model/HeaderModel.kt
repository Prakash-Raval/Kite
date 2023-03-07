package com.example.kite.dateandtime.model

data class HeaderModel(
    var header : String = "",
    var list : List<TimeSlotResponse.Data.AllTimeSlot>? = null,
    var isVisible : Boolean? = null

)
