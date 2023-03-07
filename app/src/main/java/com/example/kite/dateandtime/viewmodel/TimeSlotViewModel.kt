package com.example.kite.dateandtime.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.dateandtime.repository.TimeSlotRepository
import kotlinx.coroutines.launch

class TimeSlotViewModel(val repository: TimeSlotRepository) : ViewModel() {

    private val timeSlotMLD = MutableLiveData<TimeSlotResponse>()
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
        }

}