package com.example.kite.forgetpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.forgetpassword.model.ForgotPasswordRequest
import com.example.kite.forgetpassword.repository.ForgotPasswordRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(): ViewModelBase() {

    private var repository = ForgotPasswordRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<EmptyResponse>?>>()

    //request for email
    val request = ForgotPasswordRequest()

    //error messages
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    //fun to check validation
    fun checkEnteredEmail() {

        when {
            !Validation.isNotNull(request.email?.trim()) -> {
                errorMessage.value = ErrorEvent("Please enter email")
            }
            !request.email?.trim()?.let { Validation.isEmailValid(it) }!! -> {
                errorMessage.value = ErrorEvent("Please enter valid email")
            }
            else -> {
                errorMessage.value = ErrorEvent("")
                getForgotPasswordRequest(request)
            }
        }

    }

    private fun getForgotPasswordRequest(request: ForgotPasswordRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiForgotPassword(request)
        }
    }
}