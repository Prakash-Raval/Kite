package com.example.kite.changepassword.repository

import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.changepassword.model.ChangePasswordRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangePasswordRepository(val api: Api) : BaseRepository() {

    suspend fun callApiChangePassword(request: ChangePasswordRequest): ResponseHandler<ResponseData<EmptyResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.changePassword(request)
                })
        }
    }
}