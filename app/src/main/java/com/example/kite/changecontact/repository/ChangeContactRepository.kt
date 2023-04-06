package com.example.kite.changecontact.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.changecontact.model.ChangeContactRequest
import com.example.kite.changecontact.model.ChangeContactResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangeContactRepository(val api: Api) : BaseRepository() {

    suspend fun callApiChangeContact(request: ChangeContactRequest): ResponseHandler<ResponseData<ChangeContactResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.changeContact(request)
                })
        }
    }

}