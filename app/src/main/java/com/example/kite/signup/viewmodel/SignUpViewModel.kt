package com.example.kite.signup.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.constants.Constants
import com.example.kite.signup.model.SignUpRequest
import com.example.kite.signup.model.SignUpResponse
import com.example.kite.signup.repository.SignUpRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SignUpViewModel @Inject constructor(): ViewModelBase() {


    var signUPData = SignUpRequest()
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    private var repository = SignUpRepository(ApiClient.getApiInterface())
     var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<SignUpResponse>?>>()

    fun saveCustomer() {
        Log.d("testdata", signUPData.Firstname)
        if (signUPData.Firstname.isEmpty()) {
            errorMessage.value = ErrorEvent("Please fill first name")
        } else if (signUPData.lastName.isEmpty()) {
            errorMessage.value = ErrorEvent("Please fill last name")
        } else if (signUPData.email.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter email")
        } else if (!signUPData.email.let {
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
        ) {
            errorMessage.value = ErrorEvent("Please enter valid email")
        } else if (signUPData.mobile.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter phone number")
        } else if (signUPData.mobile <= 10.toString()) {
            errorMessage.value = ErrorEvent("Please valid phone number")
        } else if (signUPData.password.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter password")
        } else if (!signUPData.password.let {
                Constants.PASSWORD_PATTERN.matcher(it).matches()
            }) {
            errorMessage.value = ErrorEvent("Please enter valid password")
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
            getSignUpRequest(multipartBody)
        }

    }


    private fun getSignUpRequest(request: RequestBody) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiSignUp(request)
        }
    }
}



