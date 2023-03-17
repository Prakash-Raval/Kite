package com.example.kite.selectpayment.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.selectpayment.model.GetCardRequest
import com.example.kite.selectpayment.model.GetCardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCardRepository(val api: Api) : BaseRepository() {

    suspend fun callApiGetCard(request: GetCardRequest): ResponseHandler<ResponseData<GetCardResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getCard(request)
                })
        }
    }

}