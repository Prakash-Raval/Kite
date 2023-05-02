package com.example.kite.scheduletrip.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import com.example.kite.scheduletrip.repository.ScheduleTripRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleTripViewModel @Inject constructor(): ViewModelBase() {

    private var repository = ScheduleTripRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<ScheduleTripResponse>?>>()


    fun getScheduleTripData(request: ScheduleTripRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.getScheduleTripData(request)
        }
    }
}