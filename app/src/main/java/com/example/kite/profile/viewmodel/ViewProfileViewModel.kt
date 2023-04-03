package com.example.kite.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import com.example.kite.profile.repository.ViewProfileRepository
import kotlinx.coroutines.launch

class ViewProfileViewModel : ViewModelBase() {

    private var repository = ViewProfileRepository(ApiClient.getApiInterface())
    var responseDataProfile =
        MutableLiveData<ResponseHandler<ResponseData<ViewProfileResponse>?>>()

    fun getViewProfileRequest(request: ViewProfileRequest) {
        viewModelScope.launch(coroutineContext) {
            responseDataProfile.value = ResponseHandler.Loading
            responseDataProfile.value = repository.callViewProfile(request)
        }
    }
}