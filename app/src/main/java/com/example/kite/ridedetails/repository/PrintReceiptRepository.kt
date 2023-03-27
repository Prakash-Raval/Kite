package com.example.kite.ridedetails.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.ridedetails.model.PrintReceiptRequest
import com.example.kite.ridedetails.model.PrintReceiptResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PrintReceiptRepository(val api: Api) : BaseRepository() {

    suspend fun callApiReceipt(request: PrintReceiptRequest):
            ResponseHandler<ResponseData<PrintReceiptResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall (
                call = {
                    api.getPrintReceipt(request)
                })
        }
    }

}