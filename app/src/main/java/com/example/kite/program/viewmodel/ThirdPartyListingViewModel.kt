package com.example.kite.program.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.program.repository.ThirdPartyListRepository
import com.example.kite.utils.BaseResponse
import kotlinx.coroutines.launch

class ThirdPartyListingViewModel(private val repository: ThirdPartyListRepository) : ViewModel() {
    //data for observe
    private val listResult = MutableLiveData<BaseResponse<ThirdPartyListResponse>>()
    val listLiveData: LiveData<BaseResponse<ThirdPartyListResponse>>
        get() = listResult


    //calling suspend fun from repository
    suspend fun getToken(token: String) = viewModelScope.launch {
        listResult.value = BaseResponse.Loading()
        try {
            Log.d("Token_String", token)
            val response = repository.setList(
                ThirdPartyListRequest(
                    token,
                    ThirdPartyListRequest.UserLocation()
                )
            )
            if (response.isSuccessful) {
                listResult.value = BaseResponse.Success(response.body())
                //listResult.postValue(response.body())
            } else {
                // handle error
                listResult.value = BaseResponse.Error(response.message())
                Log.d("TEST-LOG1", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            listResult.value = BaseResponse.Error(e.message)
            Log.d("TEST-LOG1", e.message.toString())
        }
    }

}