package com.example.kite.forgetpassword.repository

import com.example.kite.forgetpassword.model.ForgetPasswordResponse
import com.example.kite.forgetpassword.model.ForgotPasswordRequest
import com.example.kite.network.ApiInterface
import retrofit2.Response

class ForgotPasswordRepository(val api: ApiInterface) {
    suspend fun forgotPassword(request: ForgotPasswordRequest): Response<ForgetPasswordResponse> {
        return api.forgotPassword(request)
    }
}