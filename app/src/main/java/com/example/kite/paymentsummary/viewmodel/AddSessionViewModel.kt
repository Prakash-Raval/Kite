package com.example.kite.paymentsummary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.paymentsummary.model.AddSessionResponse
import com.example.kite.paymentsummary.repository.AddSessionRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddSessionViewModel : ViewModelBase() {

    private var repository = AddSessionRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<AddSessionResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<AddSessionResponse>?>>
        get() = responseLiveData

    fun getAddCardRequest(
        access_token: RequestBody?,
        booking_id: RequestBody?,
        battery: RequestBody?,
        promoCode_id: RequestBody?,
        ride_start_document1: MultipartBody.Part?,
        ride_start_document2: MultipartBody.Part?,
        ride_start_document3: MultipartBody.Part?
    ) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiAddSession(
                access_token,
                booking_id,
                battery,
                promoCode_id,
                ride_start_document1,
                ride_start_document2,
                ride_start_document3
            )
        }
    }
}



