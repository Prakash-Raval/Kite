package com.example.kite.endridechecklist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.endridechecklist.model.EndRideResponse
import com.example.kite.endridechecklist.repository.EndRideRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EndRideViewModel : ViewModelBase() {

    val repository = EndRideRepository(ApiClient.getApiInterface())
    val responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<EndRideResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<EndRideResponse>?>>
        get() = responseLiveData

    fun getOnGoingRideRequest(
        access_token: RequestBody?,
        booking_id: RequestBody?,
        dropoff_lat: RequestBody?,
        dropoff_long: RequestBody?,
        geolocation_id: RequestBody?,
        battery: RequestBody?,
        image: MultipartBody.Part
    ) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiEndRide(
                access_token, booking_id, dropoff_lat, dropoff_long, geolocation_id, battery, image)
        }
    }
}