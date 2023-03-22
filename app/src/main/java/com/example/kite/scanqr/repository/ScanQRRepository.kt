package com.example.kite.scanqr.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scanqr.model.ScanQRRequest
import com.example.kite.scanqr.model.ScanQRResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScanQRRepository(val api: Api) : BaseRepository() {

    suspend fun callApiScanQR(request: ScanQRRequest): ResponseHandler<ResponseData<ScanQRResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.scanQRData(request)
                })
        }
    }

}