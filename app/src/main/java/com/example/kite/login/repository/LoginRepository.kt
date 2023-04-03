package com.example.kite.login.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val api: Api) : BaseRepository() {

    suspend fun callApiLogin(loginRequest: LoginRequest): ResponseHandler<ResponseData<LoginResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.setLogin(loginRequest)
                })
        }
    }
}