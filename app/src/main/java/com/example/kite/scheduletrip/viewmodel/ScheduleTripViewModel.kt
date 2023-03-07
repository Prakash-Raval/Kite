package com.example.kite.scheduletrip.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import com.example.kite.scheduletrip.repository.ScheduleTripRepository
import kotlinx.coroutines.launch

class ScheduleTripViewModel(val repository: ScheduleTripRepository) : ViewModel() {

    private val scheduleTripMLD = MutableLiveData<ScheduleTripResponse>()
    val scheduleTripLD: LiveData<ScheduleTripResponse>
        get() = scheduleTripMLD


    fun getRequiredData(request: ScheduleTripRequest) {
        getList(request)
    }

    private fun getList(request: ScheduleTripRequest) =
        viewModelScope.launch {
            val response = repository.getScheduleTripData(request)
            try {
                if (response.isSuccessful) {
                    scheduleTripMLD.postValue(response.body())
                } else {
                    Log.d("ScheduleTripResponse", response.message().toString())
                }
            } catch (e: Exception) {
                Log.d("", e.message.toString())
            }
        }

}