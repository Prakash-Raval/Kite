package com.example.kite.paymentsummary.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.paymentsummary.model.AddSessionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddSessionRepository(val api: Api) : BaseRepository() {

    suspend fun callApiAddSession(
        access_token: RequestBody?,
        booking_id: RequestBody?,
        battery: RequestBody?,
        promoCode_id: RequestBody?,
        ride_start_document1: MultipartBody.Part?,
        ride_start_document2: MultipartBody.Part?,
        ride_start_document3: MultipartBody.Part?
    ): ResponseHandler<ResponseData<AddSessionResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.startSession(
                        access_token,
                        booking_id,
                        battery,
                        promoCode_id,
                        ride_start_document1,
                        ride_start_document2,
                        ride_start_document3
                    )
                })
        }
    }

}