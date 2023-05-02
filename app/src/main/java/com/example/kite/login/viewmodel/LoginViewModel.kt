package com.example.kite.login.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import com.example.kite.login.repository.LoginRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModelBase() {
    //setting up response data

    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<LoginResponse>?>>()
    private var userModuleRepository: LoginRepository? =
        LoginRepository(ApiClient.getApiInterface())
    val password: ObservableField<String> = ObservableField("")
    var loginData = LoginRequest()
    var errorMessage = MutableLiveData<ErrorEvent<ErrorModel>>()

    init {
        password.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(value: Observable?, p1: Int) {
                if (password.get().toString().contains(" ")) {
                    errorMessage.value =
                        ErrorEvent(ErrorModel("Space not allowed in password", "Password"))
                    val str = password.get().toString()
                    password.set(str.substring(0, str.length - 1))

                }
            }
        })
    }

    //checking validation
    fun checkValidation() {
        when {
            !Validation.isNotNull(loginData.customer_email.trim()) -> {
                errorMessage.value = ErrorEvent(ErrorModel("Please enter email", "Email"))
            }
            !Validation.isEmailValid(loginData.customer_email.trim()) -> {
                errorMessage.value = ErrorEvent(ErrorModel("Please enter valid email", "Email"))

            }
            !Validation.isNotNull(password.get().toString().trim()) -> {
                errorMessage.value =
                    ErrorEvent(ErrorModel("Password minimum length should be 6", "Password"))
            }

            else -> {
                errorMessage.value =
                    ErrorEvent(ErrorModel("", "Email"))
                errorMessage.value =
                    ErrorEvent(ErrorModel("", "Password"))
                callLoginApi()

            }
        }
    }

    private fun callLoginApi() {
        loginData.password = password.get().toString().trim()
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = userModuleRepository?.callApiLogin(loginData)
        }
    }

}