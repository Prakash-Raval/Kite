package com.example.kite.forgetpassword.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.forgetpassword.model.ForgetPasswordResponse
import com.example.kite.forgetpassword.model.ForgotPasswordRequest
import com.example.kite.forgetpassword.repository.ForgotPasswordRepository
import com.example.kite.utils.BaseResponse
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(val repository: ForgotPasswordRepository) : ViewModel() {
    //data for observe
    private val mutableLiveData = MutableLiveData<BaseResponse<ForgetPasswordResponse>>()
    val liveData: LiveData<BaseResponse<ForgetPasswordResponse>>
        get() = mutableLiveData

    //request for email
    val request = ForgotPasswordRequest()

    //error messages
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    //fun to check validation
    fun checkEnteredEmail() {
        if (request.email?.isEmpty() == true) {
            errorMessage.value = ErrorEvent("Please enter email")
        } else if (!request.email?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() }!!) {
            errorMessage.value = ErrorEvent("Please enter valid email")
        } else {
            getResponse(request)
        }
    }

    //fun to get response
    private fun getResponse(request: ForgotPasswordRequest) {
        mutableLiveData.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = repository.forgotPassword(request)
                if (response.isSuccessful) {
                    mutableLiveData.value = BaseResponse.Success(response.body())
                } else {
                    mutableLiveData.value = BaseResponse.Error(response.message())
                }
            } catch (e: Exception) {
                mutableLiveData.value = BaseResponse.Error(e.message)
            }
        }
    }
}