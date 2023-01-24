package com.example.kite.addcard.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.addcard.repository.AddCardRepository
import com.example.kite.constants.Constants
import com.example.kite.login.viewmodel.ErrorModel
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch

class AddCardViewModel(private val repository: AddCardRepository) : ViewModel() {
    private val cardResult = MutableLiveData<AddCardResponse>()
    val cardLiveData: LiveData<AddCardResponse>
        get() = cardResult
    val cardRequest = AddCardRequest()

    private var errorMessage = MutableLiveData<ErrorEvent<ErrorModel>>()
    fun validate() {

    }

    //checking validation on cvv
    fun onCVVTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            errorMessage.value = ErrorEvent(ErrorModel("CVV Required", Constants.CVV))
        } else if (count != 4) {
            errorMessage.value =
                ErrorEvent(ErrorModel("CVV must be 3 to 4 digit long", Constants.CVV))
        } else {
            errorMessage.value = ErrorEvent(ErrorModel("", Constants.CVV))
        }
    }

    //checking validation on card holder name
    fun onNameTextChanged(s: CharSequence) {
        if (s.isEmpty()) {
            errorMessage.value = ErrorEvent(ErrorModel("Name Required", Constants.NAME))
        } else {
            errorMessage.value = ErrorEvent(ErrorModel("", Constants.NAME))
        }
    }

    //checking validations on card number
    fun onNumberChanged(s: CharSequence) {
        if (s.isEmpty()) {
            errorMessage.value = ErrorEvent(ErrorModel("Number Required", Constants.NUMBER))
        } else if (s.length != 16) {
            errorMessage.value = ErrorEvent(ErrorModel("Enter valid card Number", Constants.NUMBER))
        } else {
            errorMessage.value = ErrorEvent(ErrorModel("", Constants.NUMBER))

        }
    }

    init {
        viewModelScope.launch {
            try {
                val response = repository.addCard(AddCardRequest())
                if (response.isSuccessful) {

                    cardResult.postValue(response.body())
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
}



