package com.example.kite.countrylisting

import android.util.Log
import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.countrylisting.statelisting.StateRequest
import com.example.kite.countrylisting.statelisting.StateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegionRepository(private val api: Api) : BaseRepository() {

    suspend fun callApiCountry(): ResponseHandler<ResponseListData<CountryResponse>?> {

        return withContext(Dispatchers.Default) {
            val responseCountry = api.getCountryList()
            Log.d("TAG444", "callApiCountry: ${responseCountry.body()}")
            return@withContext makeAPICallForList(
                call = {
                    api.getCountryList()
                })

        }
    }

    suspend fun callApiState(stateRequest: StateRequest): ResponseHandler<ResponseListData<StateResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICallForList(
                call = {
                    api.getStateList(stateRequest)
                })
        }
    }
}