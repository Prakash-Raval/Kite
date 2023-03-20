package com.example.kite.subscription.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.subscription.model.AddSubRequest
import com.example.kite.subscription.model.AddSubResponse
import com.example.kite.subscription.model.CancelSubRequest
import com.example.kite.subscription.model.CancelSubResponse
import com.example.kite.subscription.repository.SubRepository
import kotlinx.coroutines.launch

class SubViewModel : ViewModelBase() {

    private var repository = SubRepository(ApiClient.getApiInterface())
    private var responseLiveDataADD =
        MutableLiveData<ResponseHandler<ResponseData<AddSubResponse>?>>()
    val liveDataADD: LiveData<ResponseHandler<ResponseData<AddSubResponse>?>>
        get() = responseLiveDataADD

    private var responseLiveDataCancel =
        MutableLiveData<ResponseHandler<ResponseData<CancelSubResponse>?>>()
    val liveDataCancel: LiveData<ResponseHandler<ResponseData<CancelSubResponse>?>>
        get() = responseLiveDataCancel

    fun getAddSubRequest(request: AddSubRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveDataADD.value = ResponseHandler.Loading
            responseLiveDataADD.value = repository.callApiAddSub(request)
        }
    }

    fun getCancelSubRequest(request: CancelSubRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveDataCancel.value = ResponseHandler.Loading
            responseLiveDataCancel.value = repository.callApiCancelSub(request)
        }
    }
}



