package com.example.kite.forgetpassword.repository

import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.forgetpassword.model.ForgotPasswordRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForgotPasswordRepository(val api: Api) : BaseRepository() {

    suspend fun callApiForgotPassword(request: ForgotPasswordRequest): ResponseHandler<ResponseData<EmptyResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.forgotPassword(request)
                })
        }
    }
}