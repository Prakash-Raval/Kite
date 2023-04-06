package com.example.kite.otpverification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.repository.OtpRepository
import kotlinx.coroutines.launch


class OtpViewModel : ViewModelBase() {
    private var repository = OtpRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<EmptyResponse>?>>()

    var otpData: OtpRequest? = null

    var token = ""
    fun getToken(token: String) {
        this.token = token
    }

    init {
        otpData = OtpRequest()
    }

    fun verifyOTP() {
        when {
            !Validation.isNotNull(otpData?.otpCode.toString().trim()) -> {
                showSnackBarMessage("Please enter OTP")
            }
            else -> {
                otpData?.accessToken = token
                if (token != "") {
                    otpData?.let { getOtpRequest(it) }
                }
            }
        }
    }

    private fun getOtpRequest(request: OtpRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiOTP(request)
        }
    }
}