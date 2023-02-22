package com.example.kite.statelisting

import com.example.kite.network.ApiInterface
import retrofit2.Response

class StateRepository(private val api: ApiInterface) {
    suspend fun getStateList(stateRequest: StateRequest): Response<StateResponse> {
        return api.getStateList(stateRequest)
    }
}