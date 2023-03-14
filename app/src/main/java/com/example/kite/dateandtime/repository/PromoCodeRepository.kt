package com.example.kite.dateandtime.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.dateandtime.model.PromoCodeRequest
import com.example.kite.dateandtime.model.PromoCodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PromoCodeRepository(val api: Api) : BaseRepository() {

    suspend fun callApiPromoCode(request: PromoCodeRequest): ResponseHandler<ResponseData<PromoCodeResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.promoCode(request)
                })
        }
    }

}