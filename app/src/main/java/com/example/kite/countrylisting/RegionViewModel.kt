package com.example.kite.countrylisting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.countrylisting.statelisting.StateRequest
import com.example.kite.countrylisting.statelisting.StateResponse
import kotlinx.coroutines.launch

class RegionViewModel : ViewModelBase() {


    private var repository = RegionRepository(ApiClient.getApiInterface())
    var responseLiveDataCountry =
        MutableLiveData<ResponseHandler<ResponseListData<CountryResponse>?>>()
    var responseLiveDataState =
        MutableLiveData<ResponseHandler<ResponseListData<StateResponse>?>>()

    init {
        viewModelScope.launch(coroutineContext) {
            responseLiveDataCountry.value = ResponseHandler.Loading
            responseLiveDataCountry.value = repository.callApiCountry()
            Log.d("TAG111", "init :  ${repository.callApiCountry()}")
        }
    }

    fun getStateRequest(request: StateRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveDataState.value = ResponseHandler.Loading
            responseLiveDataState.value = repository.callApiState(request)
        }
    }
}