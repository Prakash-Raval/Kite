package com.example.kite.dateandtime.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.dateandtime.repository.TimeSlotRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TimeSlotViewModel @Inject constructor() : ViewModelBase() {


    var repository = TimeSlotRepository(ApiClient.getApiInterface())
    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<TimeSlotResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<TimeSlotResponse>?>>
        get() = responseLiveData


    fun getTimeSlot(request: TimeSlotRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiTimeSlot(request)
        }
    }

}