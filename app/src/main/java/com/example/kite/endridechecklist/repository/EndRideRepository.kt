package com.example.kite.endridechecklist.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.endridechecklist.model.EndRideResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EndRideRepository(val api: Api) : BaseRepository() {

    suspend fun callApiEndRide(
        access_token: RequestBody?,
        booking_id: RequestBody?,
        dropoff_lat: RequestBody?,
        dropoff_long: RequestBody?,
        geolocation_id: RequestBody?,
        battery : RequestBody?,
        image: MultipartBody.Part
    ):
            ResponseHandler<ResponseData<EndRideResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.endRide(access_token,booking_id, dropoff_lat, dropoff_long, geolocation_id, battery, image)
                })
        }
    }
}