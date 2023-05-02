package com.example.kite.changepassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.changepassword.model.ChangePasswordRequest
import com.example.kite.changepassword.repository.ChangePasswordRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor() : ViewModelBase() {
    private var repository = ChangePasswordRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<EmptyResponse>?>>()

    var token = ""

    var requestChangePassword: ChangePasswordRequest? = null

    init {
        responseLiveData = MutableLiveData()
        requestChangePassword = ChangePasswordRequest()
    }

    fun getToken(token: String) {
        this.token = token
    }


    fun checkValidation() {
        when {
            !Validation.isNotNull(requestChangePassword?.old_password.toString().trim()) -> {
                showSnackBarMessage("Please enter current password")
            }
            (requestChangePassword?.old_password.toString().trim().length <= 6) -> {
                showSnackBarMessage("Password length should greater than 6")
            }
            !Validation.isNotNull(requestChangePassword?.new_password.toString().trim()) -> {
                showSnackBarMessage("Please enter new password")
            }
            (requestChangePassword?.new_password.toString().trim().length <= 6) -> {
                showSnackBarMessage("Password length should be greater than 6")
            }
            !Validation.isNotNull(requestChangePassword?.confirm_password.toString().trim()) -> {
                showSnackBarMessage("Please enter confirm password")
            }
            (requestChangePassword?.confirm_password.toString()
                .trim() != requestChangePassword?.new_password.toString().trim()) -> {
                showSnackBarMessage("Password not matched")
            }
            else -> {
                if (token != "") {
                    requestChangePassword?.access_token = token
                    requestChangePassword?.let { getChangePasswordRequest(it) }
                }
            }
        }
    }

    private fun getChangePasswordRequest(request: ChangePasswordRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiChangePassword(request)
        }
    }
}