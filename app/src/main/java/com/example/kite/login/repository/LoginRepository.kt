package com.example.kite.login.repository

import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import retrofit2.Response

class LoginRepository(private val signUpService: ApiInterface) {
    suspend fun setLogin(loginRequest: LoginRequest): Response<LoginResponse> {
        return signUpService.setLogin(loginRequest)
    }
}