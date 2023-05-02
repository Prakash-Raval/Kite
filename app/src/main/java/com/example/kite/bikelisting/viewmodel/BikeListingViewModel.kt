package com.example.kite.bikelisting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.bikelisting.repository.BikeListingRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class BikeListingViewModel @Inject constructor()
    : ViewModelBase() {

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