package com.example.kite.dateandtime.repository

import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.network.ApiInterface
import retrofit2.Response

class TimeSlotRepository(val apiInterface: ApiInterface) {

    suspend fun getTimeSlot(request: TimeSlotRequest): Response<TimeSlotResponse> {
        return apiInterface.getTimeSlot(request)
    }
}