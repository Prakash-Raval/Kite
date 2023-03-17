package com.example.kite.addcard.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.addcard.repository.AddCardRepository
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.utils.ErrorEvent
import kotlinx.coroutines.launch

class AddCardViewModel : ViewModelBase() {

    private var repository = AddCardRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<AddCardResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<AddCardResponse>?>>
        get() = responseLiveData

    val cardName: ObservableField<String> = ObservableField("")
    val cardNumber: ObservableField<String> = ObservableField("")
    val cardYear: ObservableField<String> = ObservableField("")
    val cardMonth: ObservableField<String> = ObservableField("")
    val cardCVV: ObservableField<String> = ObservableField("")

    val errorEvent = MutableLiveData<ErrorEvent<String>>()

    fun checkValidation() {
        when {
            !Validation.isNotNull(cardName.get().toString().trim()) -> {
                errorEvent.value = ErrorEvent("Please enter your name")
            }
            !Validation.isNotNull(cardNumber.get().toString().trim()) -> {
                errorEvent.value = ErrorEvent("Please enter card number")
            }

            ((cardNumber.toString().length <= 16)) -> {
                errorEvent.value = ErrorEvent("Please enter valid card number")
            }

            !Validation.isNotNull(cardYear.get().toString().trim()) -> {
                errorEvent.value = ErrorEvent("Please select year")
            }

            !Validation.isNotNull(cardMonth.get().toString().trim()) -> {
                errorEvent.value = ErrorEvent("Please select month")
            }

            !Validation.isNotNull(cardCVV.get().toString().trim()) -> {
                errorEvent.value = ErrorEvent("Please enter CVV")
            }

        }


        fun getAddCardRequest(request: AddCardRequest) {
            viewModelScope.launch(coroutineContext) {
                responseLiveData.value = ResponseHandler.Loading
                responseLiveData.value = repository.callApiAddCard(request)
            }
        }
    }
}


