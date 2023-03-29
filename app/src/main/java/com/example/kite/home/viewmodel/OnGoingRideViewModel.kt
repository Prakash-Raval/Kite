package com.example.kite.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.home.model.OnGoingRideRequest
import com.example.kite.home.model.OnGoingRideResponse
import com.example.kite.home.repository.OnGoingRideRepository
import kotlinx.coroutines.launch

class OnGoingRideViewModel : ViewModelBase() {

    val repository = OnGoingRideRepository(ApiClient.getApiInterface())
    val responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<OnGoingRideResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<OnGoingRideResponse>?>>
        get() = responseLiveData

    fun getOnGoingRideRequest(request: OnGoingRideRequest) {
        responseLiveData.value = ResponseHandler.Loading
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = repository.callApiOnGoingRide(request)
        }
    }
}