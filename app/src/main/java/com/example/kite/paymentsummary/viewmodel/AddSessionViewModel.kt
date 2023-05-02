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
import okhttp3.RequestBody
import javax.inject.Inject

class AddSessionViewModel @Inject constructor(): ViewModelBase() {

    private var repository = AddSessionRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<AddSessionResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<AddSessionResponse>?>>
        get() = responseLiveData

    fun getAddCardRequest(
        access_token: RequestBody?,
        booking_id: RequestBody?,
        battery: RequestBody?,
        promoCode_id: RequestBody?

    ) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiAddSession(
                access_token,
                booking_id,
                battery,
                promoCode_id

            )
        }
    }
}



