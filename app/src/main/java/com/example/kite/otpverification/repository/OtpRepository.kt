package com.example.kite.otpverification.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.otpverification.model.OtpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OtpRepository(private val api: Api) : BaseRepository() {

    suspend fun callApiOTP(request: OtpRequest): ResponseHandler<ResponseData<EmptyResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.verifyOTP(request)
                })
        }
    }
}