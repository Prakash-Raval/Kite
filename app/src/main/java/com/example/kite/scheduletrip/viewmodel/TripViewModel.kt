package com.example.kite.scheduletrip.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.TripRequest
import com.example.kite.scheduletrip.model.TripResponse
import com.example.kite.scheduletrip.repository.TripRepository
import kotlinx.coroutines.launch

class TripViewModel : ViewModelBase() {

    var repository = TripRepository(ApiClient.getApiInterface())
    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<TripResponse>?>>()

    fun getTripRequest(request: TripRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiTrip(request)
        }
    }
}