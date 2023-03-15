package com.example.kite.history.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.history.model.RideHistoryRequest
import com.example.kite.history.model.RideHistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RideHistoryRepository(val api: Api) : BaseRepository() {

    suspend fun callApiRideHistory(request: RideHistoryRequest): ResponseHandler<ResponseData<RideHistoryResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.rideHistory(request)
                })
        }
    }

}