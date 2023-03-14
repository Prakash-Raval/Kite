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

    //for getting entered login data for xml
    var loginData = LoginRequest()

    //for showing error message
    var errorMessage = MutableLiveData<ErrorEvent<ErrorModel>>()


    //checking validation
    fun checkValidation() {
        when {
            loginData.customer_email.isEmpty() -> {
                errorMessage.value = ErrorEvent(ErrorModel("Please enter email", "Email"))
            }
            !Patterns.EMAIL_ADDRESS.matcher(loginData.customer_email).matches() -> {
                errorMessage.value = ErrorEvent(ErrorModel("Please enter valid email", "Email"))
            }
            loginData.password.isEmpty() -> {
                errorMessage.value = ErrorEvent(ErrorModel("Please enter password", "Password"))
            }
            !Constants.PASSWORD_PATTERN.matcher(loginData.password).matches() -> {
                errorMessage.value =
                    ErrorEvent(ErrorModel("Password minimum length should be 6", "Password"))
            }
            else -> {
                //passing the data to request body
                loginUser(LoginRequest(loginData.customer_email, loginData.password))
            }
        }
    }

    private fun loginUser(loginRequest: LoginRequest) = viewModelScope.launch {
        try {
            //setting up the response
            val response = repository.setLogin(loginRequest)
            Log.d("Test_log 2", response.body().toString())
            if (response.isSuccessful) {
                loginResponse.postValue(response.body())
                Log.d("TEST-LOG1", response.body().toString())
            } else {
                // handle error
                Log.d("TEST-LOG2", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TEST-LOG3", e.message.toString())
        }
    }

    //method for checking on email filed
    //to show errors
    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            errorMessage.value = ErrorEvent(ErrorModel("Please enter email", "Email"))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            errorMessage.value = ErrorEvent(ErrorModel("Please enter valid email", "Email"))
        } else {
            errorMessage.value = ErrorEvent(ErrorModel("", "Email"))
        }
    }

    //method for checking on password filed
    //to show errors
    fun onPasswordTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            errorMessage.value = ErrorEvent(ErrorModel("Please enter password", "Password"))
        } else if (!Constants.PASSWORD_PATTERN.matcher(s).matches()) {
            errorMessage.value =
                ErrorEvent(ErrorModel("Password minimum length should be 6", "Password"))
        } else {
            errorMessage.value = ErrorEvent(ErrorModel("", "Password"))
        }
    }
}