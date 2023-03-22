package com.example.kite.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import com.example.kite.profile.repository.ViewProfileRepository
import kotlinx.coroutines.launch

class ViewProfileViewModel(val repository: ViewProfileRepository) : ViewModel() {

    private val profileResult = MutableLiveData<ViewProfileResponse>()
    val profileLiveData: LiveData<ViewProfileResponse>
        get() = profileResult

    var token: String = ""
    var thirdPartyID = ""

    fun getToken(token: String, thirdPartyID: String) {
        this.token = token
        this.thirdPartyID = thirdPartyID

        getCustomerProfile(ViewProfileRequest(access_token = token, third_party_id = thirdPartyID))
    }

    private fun getCustomerProfile(request: ViewProfileRequest) = viewModelScope.launch {
        try {
            val response = repository.viewProfile(request)
            if (response.isSuccessful) {

                profileResult.postValue(response.body())
            } else {
                // handle error
                Log.d("TEST-LOG1", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TEST-LOG1", e.message.toString())
        }
    }

}