package com.example.kite.bikelisting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.bikelisting.repository.BikeListingRepository
import kotlinx.coroutines.launch

class BikeListingViewModel(val repository: BikeListingRepository) : ViewModel() {

    private val bikeListingMLD = MutableLiveData<BikeListingResponse>()
    val bikeListingLD: LiveData<BikeListingResponse>
        get() = bikeListingMLD




    fun getRequiredData(request: BikeListingRequest) {
        getList(request)
    }

    private fun getList(request: BikeListingRequest) =
        viewModelScope.launch {
            val response = repository.getBikeListing(request)
            try {
                if (response.isSuccessful) {
                    bikeListingMLD.postValue(response.body())
                } else {
                    Log.d("", response.message().toString())
                }
            } catch (e: Exception) {
                Log.d("", e.message.toString())
            }
        }

}