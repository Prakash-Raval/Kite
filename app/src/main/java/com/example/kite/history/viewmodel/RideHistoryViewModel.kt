package com.example.kite.history.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.history.model.RideHistoryRequest
import com.example.kite.history.model.RideHistoryResponse
import com.example.kite.history.repository.RideHistoryRepository
import kotlinx.coroutines.launch

class RideHistoryViewModel : ViewModelBase() {

    private var repository = RideHistoryRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<RideHistoryResponse>?>>()


    fun getHistoryRequest(request: RideHistoryRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiRideHistory(request)
        }
    }
}