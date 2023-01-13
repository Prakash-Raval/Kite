package com.example.kite.login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.constants.Constants
import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import com.example.kite.login.repository.LoginRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val signUpResult = MutableLiveData<LoginResponse>()
    val signUpLiveData: LiveData<LoginResponse>
        get() = signUpResult
    var loginData = LoginRequest()
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    fun checkValidation() {
        if (loginData.email.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginData.email).matches()) {
            errorMessage.value = ErrorEvent("Please enter valid email")
        } else if (loginData.email != signUpLiveData.value?.data?.customerEmail) {
            errorMessage.value = ErrorEvent("Not Registered with us")
        } else if (loginData.password.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter password")
        } else if (!Constants.PASSWORD_PATTERN.matcher(loginData.password).matches()) {
            errorMessage.value = ErrorEvent("Please enter valid password")
        } else if (loginData.password != signUpLiveData.value?.data?.password) {
            errorMessage.value = ErrorEvent("Wrong Password")
        } else {
            loginUser(LoginRequest(loginData.email, loginData.password))
        }
    }

    private fun loginUser(loginRequest: LoginRequest) = viewModelScope.launch {
        try {
            val response = repository.setLogin(loginRequest)
            if (response.isSuccessful) {
                signUpResult.postValue(response.body())
                Log.d("TESTLOG1", response.body().toString())
            } else {
                // handle error
                Log.d("TESTLOG1", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TESTLOG1", e.message.toString())
        }
    }

}