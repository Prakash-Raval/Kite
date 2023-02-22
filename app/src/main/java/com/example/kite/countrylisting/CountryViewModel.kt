package com.example.kite.countrylisting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CountryViewModel(val repository: CountryRepository) : ViewModel() {

    private val profileResult = MutableLiveData<CountryResponse>()
    val profileLiveData: LiveData<CountryResponse>
        get() = profileResult


    fun getCountryList() = viewModelScope.launch {
        try {
            val response = repository.getCountryList()
            if (response.isSuccessful) {

                profileResult.postValue(response.body())
            } else {
                // handle error
                Log.d("TEST-LOG66", response.body().toString())
            }
        } catch (e: Exception) {
            // handle exception
            Log.d("TEST-LOG66", e.message.toString())
        }
    }

}