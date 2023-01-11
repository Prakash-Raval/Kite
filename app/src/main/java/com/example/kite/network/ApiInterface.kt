package com.example.kite.network

import com.example.kite.signup.model.SignUpRequest
import com.example.kite.signup.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("customer/add_customer")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
}