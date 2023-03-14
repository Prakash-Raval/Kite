package com.example.kite.scheduletrip.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.UpdateTripRequest
import com.example.kite.scheduletrip.model.UpdateTripResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateTripRepository(val api: Api) : BaseRepository() {

    suspend fun callApiUpdateTrip(request: UpdateTripRequest): ResponseHandler<ResponseData<UpdateTripResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.updateScheduleTrip(request)
                })
        }
    }

}