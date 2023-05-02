package com.example.kite.addcard.repository

import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddCardRepository(val api: Api) : BaseRepository() {

    suspend fun callApiAddCard(request: AddCardRequest): ResponseHandler<ResponseData<AddCardResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.addCard(request)
                })
        }
    }

}