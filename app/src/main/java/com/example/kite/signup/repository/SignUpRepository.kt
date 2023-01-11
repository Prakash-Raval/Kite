package com.example.kite.signup.repository

import com.example.kite.network.ApiInterface
import com.example.kite.signup.model.SignUpRequest
import com.example.kite.signup.model.SignUpResponse
import retrofit2.Response


class SignUpRepository(private val signUpService: ApiInterface) {
    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse> {
        return signUpService.signUp(signUpRequest)
    }
}