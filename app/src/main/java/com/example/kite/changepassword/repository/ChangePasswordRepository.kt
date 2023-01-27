package com.example.kite.changepassword.repository

import com.example.kite.changepassword.model.ChangePasswordRequest
import com.example.kite.changepassword.model.ChangePasswordResponse
import com.example.kite.network.ApiInterface
import retrofit2.Response

class ChangePasswordRepository(val api: ApiInterface) {
    suspend fun changePassword(request: ChangePasswordRequest): Response<ChangePasswordResponse> {
        return api.changePassword(request)
    }
}