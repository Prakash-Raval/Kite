package com.example.kite.otpverification.repository

import com.example.kite.network.ApiInterface
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.model.OtpResponse
import retrofit2.Response

class OtpRepository(private val apiInterface: ApiInterface) {
    suspend fun verifyOTP(otpRequest: OtpRequest): Response<OtpResponse> {
        return apiInterface.verifyOTP(otpRequest)
    }
}