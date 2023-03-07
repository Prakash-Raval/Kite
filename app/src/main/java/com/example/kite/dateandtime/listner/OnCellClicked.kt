package com.example.kite.dateandtime.listner

import com.example.kite.dateandtime.model.TimeSlotResponse

interface OnCellClicked {
    fun onClick(position1: Int, data :TimeSlotResponse.Data.AllTimeSlot,timeValue : String)
}