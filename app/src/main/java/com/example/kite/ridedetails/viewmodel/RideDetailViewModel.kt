package com.example.kite.ridedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.ridedetails.model.RideDetailRequest
import com.example.kite.ridedetails.model.RideDetailResponse
import com.example.kite.ridedetails.repository.RideDetailsRepository
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class RideDetailViewModel @Inject constructor(): ViewModelBase() {

    private var repository = RideDetailsRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<RideDetailResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<RideDetailResponse>?>>
        get() = responseLiveData

    fun getRideDetailsRequest(request: RideDetailRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiRideDetails(request)
        }
    }
}



