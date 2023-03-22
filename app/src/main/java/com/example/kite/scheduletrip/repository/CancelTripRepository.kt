package com.example.kite.scheduletrip.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.CancelTripRequest
import com.example.kite.scheduletrip.model.CancelTripResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CancelTripRepository(val api: Api) : BaseRepository() {

    suspend fun callApiCancelTrip(request: CancelTripRequest): ResponseHandler<ResponseData<CancelTripResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.cancelScheduleTrip(request)
                })
        }
    }

}