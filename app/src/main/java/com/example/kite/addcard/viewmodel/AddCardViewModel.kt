package com.example.kite.addcard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.addcard.repository.AddCardRepository
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCardViewModel @Inject constructor(): ViewModelBase() {

    private var repository = AddCardRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<AddCardResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<AddCardResponse>?>>
        get() = responseLiveData

    fun getAddCardRequest(request: AddCardRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiAddCard(request)
        }
    }
}



