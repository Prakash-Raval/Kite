package com.example.kite.profile.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewProfileRepository(private val api: Api) : BaseRepository() {

    suspend fun callViewProfile(request: ViewProfileRequest): ResponseHandler<ResponseData<ViewProfileResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getViewProfile(request)
                })
        }
    }
}