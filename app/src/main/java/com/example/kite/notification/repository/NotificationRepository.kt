package com.example.kite.notification.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.notification.model.NotificationRequest
import com.example.kite.notification.model.NotificationResponse
import com.example.kite.notification.model.UpdateNotificationRequest
import com.example.kite.notification.model.UpdateNotificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationRepository(val api: Api) : BaseRepository() {

    suspend fun callApiNotification(request: NotificationRequest): ResponseHandler<ResponseData<NotificationResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getNotificationListing(request)
                })
        }
    }

    /*   suspend fun callApiNotificationUpdate(
           token: String,
           read: String,
           notify: String
       ): ResponseHandler<ResponseData<UpdateNotificationResponse>?> {
           return withContext(Dispatchers.Default) {
               return@withContext makeAPICall(
                   call = {
                       api.updateNotificationListing(token, read, notify)
                   })
           }
       }*/
    suspend fun callApiNotificationUpdate(request: UpdateNotificationRequest): ResponseHandler<ResponseData<UpdateNotificationResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.updateNotificationListing(request)
                })
        }
    }

}