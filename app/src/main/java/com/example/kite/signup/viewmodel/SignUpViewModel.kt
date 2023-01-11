package com.example.kite.signup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kite.constants.Constants
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.signup.model.SignUpRequest
import com.example.kite.signup.model.SignUpResponse
import com.example.kite.signup.repository.SignUpRepository

class SignUpViewModel : ViewModel() {
    private val signUpResult = MutableLiveData<SignUpResponse>()
    private val signUpService: ApiInterface =
        RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
    private val repository = SignUpRepository(signUpService)

    val signUpLiveData: LiveData<SignUpResponse>
        get() = signUpResult


    suspend fun signUp(signUpRequest: SignUpRequest) {
        try {
            val response = repository.signUp(signUpRequest)
            if (response.isSuccessful) {
                signUpResult.postValue(response.body())
            } else {
                // handle error
                Log.d("TESTLOG1", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TESTLOG1", e.message.toString())
        }
    }

    fun validation() {

    }

    fun submitData() {

    }
}



