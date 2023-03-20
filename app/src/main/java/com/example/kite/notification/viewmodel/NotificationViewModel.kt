package com.example.kite.notification.viewmodel

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
import com.example.kite.notification.model.NotificationRequest
import com.example.kite.notification.model.NotificationResponse
import com.example.kite.notification.model.UpdateNotificationRequest
import com.example.kite.notification.model.UpdateNotificationResponse
import com.example.kite.notification.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModelBase() {

    private var repository = NotificationRepository(ApiClient.getApiInterface())
    private var responseLiveData =
        MutableLiveData<ResponseHandler<ResponseData<NotificationResponse>?>>()
    val liveData: LiveData<ResponseHandler<ResponseData<NotificationResponse>?>>
        get() = responseLiveData


    private var responseLiveDataUpdate =
        MutableLiveData<ResponseHandler<ResponseData<UpdateNotificationResponse>?>>()
    val liveDataUpdate : LiveData<ResponseHandler<ResponseData<UpdateNotificationResponse>?>>
        get() = responseLiveDataUpdate

    fun getNotificationListing(request: NotificationRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = repository.callApiNotification(request)
        }
    }


    fun updateNotification(request: UpdateNotificationRequest) {
        viewModelScope.launch(coroutineContext) {
            responseLiveDataUpdate.value = ResponseHandler.Loading
            responseLiveDataUpdate.value = repository.callApiNotificationUpdate(request)
        }
    }
}



