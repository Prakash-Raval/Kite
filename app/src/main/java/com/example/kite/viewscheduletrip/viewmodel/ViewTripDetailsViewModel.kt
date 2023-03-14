package com.example.kite.viewscheduletrip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.viewscheduletrip.model.ViewTripRequest
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import com.example.kite.viewscheduletrip.repository.ViewTripDetailsRepository
import kotlinx.coroutines.launch

class ViewTripDetailsViewModel : ViewModelBase() {

    private var repository = ViewTripDetailsRepository(ApiClient.getApiInterface())
    private var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<ViewTripResponse>?>>()
    val liveData : LiveData<ResponseHandler<ResponseData<ViewTripResponse>?>>
        get() = responseLiveData

    fun getTripRequest(request: ViewTripRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiDetailsTrip(request)
        }
    }
}