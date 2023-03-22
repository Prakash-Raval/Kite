package com.example.kite.scanqr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scanqr.model.ScanQRRequest
import com.example.kite.scanqr.model.ScanQRResponse
import com.example.kite.scanqr.repository.ScanQRRepository
import kotlinx.coroutines.launch

class ScanQRViewModel : ViewModelBase() {

    private var repository = ScanQRRepository(ApiClient.getApiInterface())
    private var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<ScanQRResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<ScanQRResponse>?>>
        get() = responseLiveData

    fun getScanQRRequest(request: ScanQRRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiScanQR(request)
        }
    }
}