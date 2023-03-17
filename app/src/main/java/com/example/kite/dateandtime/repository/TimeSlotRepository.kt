package com.example.kite.dateandtime.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.dateandtime.model.PromoCodeRequest
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.network.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TimeSlotRepository(val api: Api) : BaseRepository() {

    /*suspend fun getTimeSlot(request: TimeSlotRequest): Response<TimeSlotResponse> {
        return apiInterface.getTimeSlot(request)
    }*/

    suspend fun callApiTimeSlot(request: TimeSlotRequest): ResponseHandler<ResponseData<TimeSlotResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getTimeSlot(request)
                })
        }
    }
}