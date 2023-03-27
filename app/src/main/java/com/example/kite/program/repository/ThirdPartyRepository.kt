package com.example.kite.program.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ThirdPartyRepository(val api: Api) : BaseRepository() {

    suspend fun callApiListing(request: ThirdPartyListRequest):
            ResponseHandler<ResponseListData<ThirdPartyListResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICallForList(
                call = {
                    api.getThirdPartyList(request)
                })
        }
    }

}