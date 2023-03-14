package com.example.kite.scheduletrip.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.TripRequest
import com.example.kite.scheduletrip.model.TripResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripRepository(val api: Api) : BaseRepository() {

    suspend fun callApiTrip(request: TripRequest): ResponseHandler<ResponseData<TripResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.addScheduleTrip(request)
                })
        }
    }

}