package com.example.kite.bikelisting.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BikeListingRepository(val api: Api) : BaseRepository() {

    suspend fun getBikeListing(request: BikeListingRequest): ResponseHandler<ResponseData<BikeListingResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getBikeListing(request)
                })
        }
    }
}