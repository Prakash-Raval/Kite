package com.example.kite.scheduletrip.repository

import com.example.kite.network.ApiInterface
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import retrofit2.Response

class ScheduleTripRepository(val apiInterface: ApiInterface) {

    suspend fun getScheduleTripData(request: ScheduleTripRequest): Response<ScheduleTripResponse> {
        return apiInterface.getScheduleTripData(request)
    }
}