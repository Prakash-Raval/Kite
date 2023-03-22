package com.example.kite.base.network.client

import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.constants.Constants
import com.example.kite.dateandtime.model.PromoCodeRequest
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.history.model.RideHistoryRequest
import com.example.kite.history.model.RideHistoryResponse
import com.example.kite.notification.model.NotificationRequest
import com.example.kite.notification.model.NotificationResponse
import com.example.kite.notification.model.UpdateNotificationRequest
import com.example.kite.notification.model.UpdateNotificationResponse
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.scanqr.model.ScanQRRequest
import com.example.kite.scanqr.model.ScanQRResponse
import com.example.kite.scheduletrip.model.*
import com.example.kite.selectpayment.model.GetCardRequest
import com.example.kite.selectpayment.model.GetCardResponse
import com.example.kite.subscription.model.AddSubRequest
import com.example.kite.subscription.model.AddSubResponse
import com.example.kite.subscription.model.CancelSubRequest
import com.example.kite.subscription.model.CancelSubResponse
import com.example.kite.viewscheduletrip.model.ViewTripRequest
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {


    @POST(Constants.PROMO_CODE)
    suspend fun promoCode(@Body promoCodeRequest: PromoCodeRequest): Response<ResponseData<PromoCodeResponse>>

    @POST(Constants.ADD_SCHEDULE_TRIP)
    suspend fun addScheduleTrip(@Body tripRequest: TripRequest): Response<ResponseData<TripResponse>>

    @POST(Constants.RESERVATION_LIST)
    suspend fun viewScheduleTrip(@Body tripRequest: ListReservationRequest): Response<ResponseData<ListReservationResponse>>

    @POST(Constants.VIEW_SCHEDULE_TRIP)
    suspend fun viewDetailsScheduleTrip(@Body tripRequest: ViewTripRequest): Response<ResponseData<ViewTripResponse>>

    @POST(Constants.UPDATE_SCHEDULE_TRIP)
    suspend fun updateScheduleTrip(@Body tripRequest: UpdateTripRequest): Response<ResponseData<UpdateTripResponse>>

    @POST(Constants.CANCEL_SCHEDULE_TRIP)
    suspend fun cancelScheduleTrip(@Body tripRequest: CancelTripRequest): Response<ResponseData<CancelTripResponse>>

    @POST(Constants.SCAN_QR)
    suspend fun scanQRData(@Body scanQRRequest: ScanQRRequest): Response<ResponseData<ScanQRResponse>>

    @POST(Constants.ADD_CARD)
    suspend fun addCard(@Body addCardRequest: AddCardRequest): Response<ResponseData<AddCardResponse>>

    @POST(Constants.GET_CARD)
    suspend fun getCard(@Body addCardRequest: GetCardRequest): Response<ResponseData<GetCardResponse>>

    @POST(Constants.RIDE_HISTORY)
    suspend fun rideHistory(@Body addCardRequest: RideHistoryRequest): Response<ResponseData<RideHistoryResponse>>

    @POST(Constants.TIME_SLOT)
    suspend fun getTimeSlot(@Body request: TimeSlotRequest): Response<ResponseData<TimeSlotResponse>>

    @POST(Constants.ADD_SUBSCRIPTION)
    suspend fun addSubscription(@Body request: AddSubRequest): Response<ResponseData<AddSubResponse>>

    @POST(Constants.CANCEL_SUBSCRIPTION)
    suspend fun cancelSubscription(@Body request: CancelSubRequest): Response<ResponseData<CancelSubResponse>>

 /*   @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST(Constants.NOTIFICATION_UPDATE)
    suspend fun updateNotificationListing(
        @Field("access_token") access_token: String,
        @Field("is_read") is_read: String,
        @Field("notification_id") notification_id: String
    ): Response<ResponseData<UpdateNotificationResponse>>*/


    @POST(Constants.NOTIFICATION_UPDATE)
    suspend fun updateNotificationListing(
        @Body req:UpdateNotificationRequest
    ): Response<ResponseData<UpdateNotificationResponse>>

    @POST(Constants.NOTIFICATION_LISTING)
    suspend fun getNotificationListing(@Body request: NotificationRequest): Response<ResponseData<NotificationResponse>>
}