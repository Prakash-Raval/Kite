package com.example.kite.changecontact.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.changecontact.model.ChangeContactRequest
import com.example.kite.changecontact.model.ChangeContactResponse
import com.example.kite.changecontact.repository.ChangeContactRepository
import kotlinx.coroutines.launch


class ChangeContactViewModel : ViewModelBase() {
    private var repository = ChangeContactRepository(ApiClient.getApiInterface())
    var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<ChangeContactResponse>?>>()

    var changeContact: ChangeContactRequest? = null

    var token = ""
    var mobile = ""

    fun getToken(token: String, mobile: String) {
        this.token = token
        this.mobile = mobile
        changeContact?.phoneNumber = mobile
    }


    init {
        changeContact = ChangeContactRequest()
        Log.d("TA11G", "init : $mobile")
    }

    fun verifyContact() {
        when {
            (changeContact?.phoneNumber.toString().trim() == mobile) -> {
                showSnackBarMessage("Please update phone number")

            }
            !Validation.isNotNull(changeContact?.phoneNumber.toString().trim()) -> {
                showSnackBarMessage("Please enter phone number")
            }
            (changeContact?.phoneNumber.toString().trim().length != 10) -> {
                showSnackBarMessage("Please enter valid phone number")

            }
            else -> {
                changeContact?.accessToken = token
                if (token != "") {
                    changeContact?.let { getContactRequest(it) }
                }
            }
        }
    }

    private fun getContactRequest(request: ChangeContactRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiChangeContact(request)
        }
    }
}