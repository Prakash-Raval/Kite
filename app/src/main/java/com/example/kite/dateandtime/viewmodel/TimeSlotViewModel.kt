package com.example.kite.dateandtime.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.dateandtime.model.PromoCodeRequest
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.dateandtime.repository.PromoCodeRepository
import com.example.kite.dateandtime.repository.TimeSlotRepository
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch

class TimeSlotViewModel : ViewModelBase() {

/*    private val timeSlotMLD = MutableLiveData<TimeSlotResponse>()
    val timeSlotLD: LiveData<TimeSlotResponse>
        get() = timeSlotMLD


    fun getRequiredData(request: TimeSlotRequest) {
        getList(request)
    }

    private fun getList(request: TimeSlotRequest) =
        viewModelScope.launch {
            val response = repository.getTimeSlot(request)
            try {
                if (response.isSuccessful) {
                    timeSlotMLD.postValue(response.body())
                    Log.d("TIMESLOTDATA", response.toString())
                } else {
                    Log.d("TIMESLOTDATA", response.message().toString())
                }
            } catch (e: Exception) {
                Log.d("TIMESLOTDATA", e.message.toString())
            }
        }*/

    var repository = TimeSlotRepository(ApiClient.getApiInterface())
    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<TimeSlotResponse>?>>()
    val liveData :LiveData<ResponseHandler<ResponseData<TimeSlotResponse>?>>
    get() = responseLiveData



    fun getTimeSlot(request: TimeSlotRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiTimeSlot(request)
        }
    }

}