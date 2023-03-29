package com.example.kite.home.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.home.model.OnGoingRideRequest
import com.example.kite.home.model.OnGoingRideResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OnGoingRideRepository(val api: Api) : BaseRepository() {

    suspend fun callApiOnGoingRide(request: OnGoingRideRequest):
            ResponseHandler<ResponseData<OnGoingRideResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getOnGoingRide(request)
                })
        }
    }
}