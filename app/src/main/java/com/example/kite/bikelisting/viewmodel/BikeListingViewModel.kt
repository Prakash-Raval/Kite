package com.example.kite.bikelisting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.addcard.repository.AddCardRepository
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.bikelisting.repository.BikeListingRepository
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch

class BikeListingViewModel : ViewModelBase() {

    /*private val bikeListingMLD = MutableLiveData<BikeListingResponse>()
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
        }*/


    private var repository = BikeListingRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<BikeListingResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<BikeListingResponse>?>>
        get() = responseLiveData

    fun getAddCardRequest(request: BikeListingRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.getBikeListing(request)
        }
    }
}