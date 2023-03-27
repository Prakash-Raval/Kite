package com.example.kite.ridedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.ridedetails.model.PrintReceiptRequest
import com.example.kite.ridedetails.model.PrintReceiptResponse
import com.example.kite.ridedetails.repository.PrintReceiptRepository
import kotlinx.coroutines.launch

class PrintReceiptViewModel : ViewModelBase() {

    private var repository = PrintReceiptRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<PrintReceiptResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<PrintReceiptResponse>?>>
        get() = responseLiveData

    fun getThirdPartyList(request: PrintReceiptRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiReceipt(request)
        }
    }
}



