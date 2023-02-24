package com.example.kite.statelisting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StateViewModel(val repository: StateRepository) : ViewModel() {

    private val profileResult = MutableLiveData<StateResponse>()
    val profileLiveData: LiveData<StateResponse>
        get() = profileResult

    fun getStateList(request: StateRequest) = viewModelScope.launch {
        try {
            val response = repository.getStateList(request)
            if (response.isSuccessful) {
                profileResult.postValue(response.body())
                Log.d("TEST-LOG77", response.body().toString())
            } else {
                // handle error
                Log.d("TEST-LOG77", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TEST-LOG77", e.message.toString())
        }
    }

}