package com.example.kite.signup.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.constants.Constants
import com.example.kite.signup.model.SignUpRequest
import com.example.kite.signup.model.SignUpResponse2
import com.example.kite.signup.repository.SignUpRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    private val signUpResult = MutableLiveData<SignUpResponse2>()
    val signUpLiveData: LiveData<SignUpResponse2>
        get() = signUpResult
    var signUPData = SignUpRequest()
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage


    fun saveCustomer() {
        Log.d("testdata", signUPData.Firstname)
        if (signUPData.Firstname.isEmpty()) {
            errorMessage.value = ErrorEvent("Please fill first name")
        } else if (signUPData.lastName.isEmpty()) {
            errorMessage.value = ErrorEvent("Please fill last name")
        } else if (signUPData.email.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter Email")
        } else if (!signUPData.email.let {
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
        ) {
            errorMessage.value = ErrorEvent("Please enter valid Email")
        } else if (signUPData.mobile.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter Phone number")
        } else if (signUPData.mobile <= 10.toString()) {
            errorMessage.value = ErrorEvent("Please valid phone number")
        } else if (signUPData.password.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter password")
        } else if (!signUPData.password.let {
                Constants.PASSWORD_PATTERN.matcher(it).matches()
            }) {
            errorMessage.value = ErrorEvent("Please enter valid Password")
        } else if (signUPData.confirmPassword != signUPData.password) {
            errorMessage.value = ErrorEvent("confirm password should match to password")
        } else {
            val multipartBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("signup_type", "1")
                .addFormDataPart("device_type", "2")
                .addFormDataPart("device_token", "1234567893")
                .addFormDataPart("country_code", "+91")
                .addFormDataPart("lang", "0")
                .addFormDataPart("confirmPassword", signUPData.confirmPassword)
                .addFormDataPart("password", signUPData.password)
                .addFormDataPart("country", "Canada")
                .addFormDataPart("customer_profile_picture\"; filename=\"image.png", "")
                .addFormDataPart("customer_phone_number", signUPData.mobile)
                .addFormDataPart("customer_email", signUPData.email)
                .addFormDataPart("customer_last_name", signUPData.lastName)
                .addFormDataPart("customer_first_name", signUPData.Firstname)
                .build()
            registerCustomer(multipartBody)
        }

    }

    private fun registerCustomer(requestBody: RequestBody) = viewModelScope.launch {
        try {
            val response = repository.setSignUp(requestBody)
            if (response.isSuccessful) {

                signUpResult.postValue(response.body())
            } else {
                // handle error
                errorMessage.value = ErrorEvent(response.message())
                Log.d("TEST-LOG1", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TEST-LOG1", e.message.toString())
        }
    }


}



