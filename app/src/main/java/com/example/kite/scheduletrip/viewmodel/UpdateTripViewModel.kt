package com.example.kite.scheduletrip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.UpdateTripRequest
import com.example.kite.scheduletrip.model.UpdateTripResponse
import com.example.kite.scheduletrip.repository.UpdateTripRepository
import kotlinx.coroutines.launch

class UpdateTripViewModel : ViewModelBase() {

    var repository = UpdateTripRepository(ApiClient.getApiInterface())
    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<UpdateTripResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<UpdateTripResponse>?>>
        get() = responseLiveData

    fun getTripRequest(request: UpdateTripRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiUpdateTrip(request)
        }
    }
}