package com.example.kite.ridedetails.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.ridedetails.model.RideDetailRequest
import com.example.kite.ridedetails.model.RideDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RideDetailsRepository(val api: Api) : BaseRepository() {

    suspend fun callApiRideDetails(request: RideDetailRequest):
            ResponseHandler<ResponseData<RideDetailResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getRideDetails(request)
                })
        }
    }

}