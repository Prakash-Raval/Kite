package com.example.kite.otpverification.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.model.OtpResponse
import com.example.kite.otpverification.repository.OtpRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtpViewModel(val repository: OtpRepository) :
    ViewModel() {
    private val otpResult = MutableLiveData<OtpResponse>()
    val otpLiveData: LiveData<OtpResponse>
        get() = otpResult

    var isCheck: Boolean = false


    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    var otpData = OtpRequest()

    private lateinit var token: String

    fun otpCheck(token: String) {
        this.token = token
    }

    fun checkValidation() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response =
                    repository.verifyOTP(OtpRequest("0", token, otpData.otpCode))
                Log.d("Token String", token)
                if (response.isSuccessful) {
                    otpResult.postValue(response.body())
                } else {
                    // handle error
                    errorMessage.value = ErrorEvent(response.message())
                    Log.d("TEST-LOG1", response.body().toString())
                }
            } catch (e: Exception) {
                // handle exception
                Log.d("TEST-LOG2", e.message.toString())
            }
        }
        if (otpData.otpCode == null) {
            errorMessage.value = ErrorEvent("Please fill OTP")
        } else {
            errorMessage.value = ErrorEvent("Wrong Otp Entered")
            isCheck = true
        }
    }
}