package com.example.kite.dateandtime.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.dateandtime.model.PromoCodeRequest
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.dateandtime.repository.PromoCodeRepository
import kotlinx.coroutines.launch

class PromoCodeViewModel : ViewModelBase() {

    var observableFieldPromoCode: ObservableField<String> = ObservableField("")
    var promoCodeRequest: PromoCodeRequest? = null
    var repository = PromoCodeRepository(ApiClient.getApiInterface())
    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<PromoCodeResponse>?>>()

    init {
        promoCodeRequest = PromoCodeRequest()
    }

    fun checkPromoCode(token: String) {
        promoCodeRequest?.promocode = observableFieldPromoCode.toString()
        promoCodeRequest?.access_token = token
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = promoCodeRequest?.let { repository.callApiPromoCode(it) }
        }
    }
}