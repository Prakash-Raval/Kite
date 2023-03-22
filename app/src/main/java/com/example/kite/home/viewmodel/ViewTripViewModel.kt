package com.example.kite.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.home.repository.ViewTripRepository
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import kotlinx.coroutines.launch

class ViewTripViewModel : ViewModelBase() {

    val repository = ViewTripRepository(ApiClient.getApiInterface())
    val responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<ListReservationResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<ListReservationResponse>?>>
        get() = responseLiveData

    fun getViewTripRequest(request: ListReservationRequest) {
        responseLiveData.value = ResponseHandler.Loading
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = repository.callApiViewTrip(request)
            Log.d("HomeFragment", "getViewTripRequest: " + responseLiveData.value)
        }
    }


}