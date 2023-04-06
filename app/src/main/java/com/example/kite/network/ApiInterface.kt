package com.example.kite.network

import com.example.kite.constants.Constants
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.model.OtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {


    @POST(Constants.OTP_URL)
    suspend fun verifyOTP(@Body body: OtpRequest): Response<OtpResponse>


}