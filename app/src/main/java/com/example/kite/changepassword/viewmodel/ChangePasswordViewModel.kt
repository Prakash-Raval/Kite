package com.example.kite.changepassword.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.changepassword.model.ChangePasswordRequest
import com.example.kite.changepassword.model.ChangePasswordResponse
import com.example.kite.changepassword.repository.ChangePasswordRepository
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch

class ChangePasswordViewModel(val repository: ChangePasswordRepository) : ViewModel() {

    //data for observe
    private val mutableLiveData = MutableLiveData<ChangePasswordResponse>()
    val liveData: LiveData<ChangePasswordResponse>
        get() = mutableLiveData

    //request data
    val request = ChangePasswordRequest()

    //error messages
    private val errorMessage = MutableLiveData<ErrorEvent<String>>()
    val errorLiveData: LiveData<ErrorEvent<String>>
        get() = errorMessage

    var token = ""
    fun getToken(token: String) {
        this.token = token
    }


    fun changePassword() {
        if (request.old_password.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter your current password")
        } else if (request.new_password.isEmpty()) {
            errorMessage.value = ErrorEvent("Please enter your new password")
        }  else {
            callData(ChangePasswordRequest(token, request.new_password, request.old_password))
        }
    }

    private fun callData(request: ChangePasswordRequest) = viewModelScope.launch {
        try {
            val response = repository.changePassword(request)
            if (response.isSuccessful) {
                Log.d("PasswordChange", response.body().toString())
            } else {
                Log.d("PasswordChange", response.message())
            }
        } catch (e: Exception) {
            Log.d("PasswordChange", e.message.toString())
        }
    }


}