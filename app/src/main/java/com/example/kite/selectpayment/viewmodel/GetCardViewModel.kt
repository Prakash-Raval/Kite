package com.example.kite.selectpayment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.selectpayment.model.GetCardRequest
import com.example.kite.selectpayment.model.GetCardResponse
import com.example.kite.selectpayment.repository.GetCardRepository
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCardViewModel @Inject constructor(): ViewModelBase() {

    private var repository = GetCardRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<GetCardResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<GetCardResponse>?>>
        get() = responseLiveData

    fun getGetCardRequest(request: GetCardRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiGetCard(request)
        }
    }
}



