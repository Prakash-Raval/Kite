package com.example.kite.program.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.program.repository.ThirdPartyRepository
import kotlinx.coroutines.launch

class ThirdPartyViewModel : ViewModelBase() {

    private var repository = ThirdPartyRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseListData<ThirdPartyListResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseListData<ThirdPartyListResponse>?>>
        get() = responseLiveData

    fun getThirdPartyList(request: ThirdPartyListRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiListing(request)
        }
    }
}



