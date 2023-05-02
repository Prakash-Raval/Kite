package com.example.kite.otpverification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.otpverification.model.PhoneRequest
import com.example.kite.otpverification.repository.PhoneRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhoneViewModel @Inject constructor() : ViewModelBase() {

    private var repository = PhoneRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<EmptyResponse>?>>()


    fun getPhone(request: PhoneRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.apiCallPhoneChange(request)
        }
    }
}



