package com.example.kite.bikelisting.repository

import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.network.ApiInterface
import retrofit2.Response

class BikeListingRepository(val apiInterface: ApiInterface) {

    suspend fun getBikeListing(request: BikeListingRequest): Response<BikeListingResponse> {
        return apiInterface.getBikeListing(request)
    }
}