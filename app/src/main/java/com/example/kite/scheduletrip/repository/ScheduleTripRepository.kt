package com.example.kite.scheduletrip.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleTripRepository(val api: Api) : BaseRepository() {


    suspend fun getScheduleTripData(request: ScheduleTripRequest): ResponseHandler<ResponseData<ScheduleTripResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getScheduleTripData(request)
                })
        }
    }
}