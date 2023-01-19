package com.example.kite.program.repository

import com.example.kite.network.ApiInterface
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import retrofit2.Response

class ThirdPartyListRepository(private val apiInterface: ApiInterface) {

    suspend fun setList(programListRequest: ThirdPartyListRequest): Response<ThirdPartyListResponse> {
        return apiInterface.setList(programListRequest)
    }
}