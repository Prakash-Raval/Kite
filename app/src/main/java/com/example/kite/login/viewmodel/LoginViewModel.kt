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
    //setting up response data
    private val loginResponse = MutableLiveData<LoginResponse>()
    val loginLiveData: LiveData<LoginResponse>
        get() = loginResponse

    //for getting entered login data
    var loginData = LoginRequest()

    //for showing error message
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    //checking validation
    fun checkValidation() {
        if (loginData.customer_email.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginData.customer_email).matches()) {
            errorMessage.value = ErrorEvent("Please enter valid email")
        } else if (loginData.password.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter password")
        } else if (!Constants.PASSWORD_PATTERN.matcher(loginData.password).matches()) {
            errorMessage.value = ErrorEvent("Please enter valid password")
        } else {
            //passing the data to request body
            loginUser(LoginRequest(loginData.customer_email, loginData.password))
        }
    }

    private fun loginUser(loginRequest: LoginRequest) = viewModelScope.launch {
        try {
            //setting up the response
            val response = repository.setLogin(loginRequest)
            Log.d("Test_log 2", response.body().toString())
            if (response.isSuccessful) {
                loginResponse.postValue(response.body())
                errorMessage.value = ErrorEvent("Login successfully")
                Log.d("TEST-LOG1", response.body().toString())
            } else {
                // handle error
                errorMessage.value = ErrorEvent("Login Unsuccessfully")
                Log.d("TEST-LOG2", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TEST-LOG3", e.message.toString())
        }
    }

}