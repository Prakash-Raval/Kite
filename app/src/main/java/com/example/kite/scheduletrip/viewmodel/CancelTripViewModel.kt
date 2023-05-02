package com.example.kite.scheduletrip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.CancelTripRequest
import com.example.kite.scheduletrip.model.CancelTripResponse
import com.example.kite.scheduletrip.repository.CancelTripRepository
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class CancelTripViewModel @Inject constructor(): ViewModelBase() {

    var repository = CancelTripRepository(ApiClient.getApiInterface())
    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<CancelTripResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<CancelTripResponse>?>>
        get() = responseLiveData

    fun getCancelTripRequest(request: CancelTripRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiCancelTrip(request)
        }
    }
}