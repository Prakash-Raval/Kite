package com.example.kite.subscription.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.subscription.model.AddSubRequest
import com.example.kite.subscription.model.AddSubResponse
import com.example.kite.subscription.model.CancelSubRequest
import com.example.kite.subscription.model.CancelSubResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubRepository(val api: Api) : BaseRepository() {

    suspend fun callApiAddSub(request: AddSubRequest): ResponseHandler<ResponseData<AddSubResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.addSubscription(request)
                })
        }
    }

    suspend fun callApiCancelSub(request: CancelSubRequest): ResponseHandler<ResponseData<CancelSubResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.cancelSubscription(request)
                })
        }
    }

}