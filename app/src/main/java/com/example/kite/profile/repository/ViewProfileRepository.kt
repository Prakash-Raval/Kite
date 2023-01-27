package com.example.kite.profile.repository

import com.example.kite.network.ApiInterface
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import retrofit2.Response

class ViewProfileRepository(private val api: ApiInterface) {
    suspend fun viewProfile(view: ViewProfileRequest): Response<ViewProfileResponse> {
        return api.viewProfile(view)
    }
}