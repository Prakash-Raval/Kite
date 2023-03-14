package com.example.kite.viewscheduletrip.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.TripRequest
import com.example.kite.scheduletrip.model.TripResponse
import com.example.kite.viewscheduletrip.model.ViewTripRequest
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewTripDetailsRepository(val api: Api) : BaseRepository() {

    suspend fun callApiDetailsTrip(request: ViewTripRequest): ResponseHandler<ResponseData<ViewTripResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.viewDetailsScheduleTrip(request)
                })
        }
    }

}