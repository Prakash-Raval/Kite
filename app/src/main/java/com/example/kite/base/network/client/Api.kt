package com.example.kite.base.network.client

import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.network.model.ResponseListData
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
import com.example.kite.paymentsummary.model.AddSessionResponse
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.ridedetails.model.PrintReceiptRequest
import com.example.kite.ridedetails.model.PrintReceiptResponse
import com.example.kite.ridedetails.model.RideDetailRequest
import com.example.kite.ridedetails.model.RideDetailResponse
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {


    @POST(Constants.PROMO_CODE)
    suspend fun promoCode(@Body request: PromoCodeRequest): Response<ResponseData<PromoCodeResponse>>

    @POST(Constants.ADD_SCHEDULE_TRIP)
    suspend fun addScheduleTrip(@Body request: TripRequest): Response<ResponseData<TripResponse>>

    @POST(Constants.RESERVATION_LIST)
    suspend fun viewScheduleTrip(@Body request: ListReservationRequest): Response<ResponseData<ListReservationResponse>>

    @POST(Constants.VIEW_SCHEDULE_TRIP)
    suspend fun viewDetailsScheduleTrip(@Body request: ViewTripRequest): Response<ResponseData<ViewTripResponse>>

    @POST(Constants.UPDATE_SCHEDULE_TRIP)
    suspend fun updateScheduleTrip(@Body request: UpdateTripRequest): Response<ResponseData<UpdateTripResponse>>

    @POST(Constants.CANCEL_SCHEDULE_TRIP)
    suspend fun cancelScheduleTrip(@Body request: CancelTripRequest): Response<ResponseData<CancelTripResponse>>

    @POST(Constants.SCAN_QR)
    suspend fun scanQRData(@Body request: ScanQRRequest): Response<ResponseData<ScanQRResponse>>

    @POST(Constants.ADD_CARD)
    suspend fun addCard(@Body request: AddCardRequest): Response<ResponseData<AddCardResponse>>

    @POST(Constants.GET_CARD)
    suspend fun getCard(@Body request: GetCardRequest): Response<ResponseData<GetCardResponse>>

    @POST(Constants.RIDE_HISTORY)
    suspend fun rideHistory(@Body request: RideHistoryRequest): Response<ResponseData<RideHistoryResponse>>

    @POST(Constants.TIME_SLOT)
    suspend fun getTimeSlot(@Body request: TimeSlotRequest): Response<ResponseData<TimeSlotResponse>>

    @POST(Constants.ADD_SUBSCRIPTION)
    suspend fun addSubscription(@Body request: AddSubRequest): Response<ResponseData<AddSubResponse>>

    @POST(Constants.CANCEL_SUBSCRIPTION)
    suspend fun cancelSubscription(@Body request: CancelSubRequest): Response<ResponseData<CancelSubResponse>>

    @POST(Constants.THIRD_PARTY_LIST)
    suspend fun getThirdPartyList(@Body listRequest: ThirdPartyListRequest):
            Response<ResponseListData<ThirdPartyListResponse>>

    @Multipart
    @POST(Constants.START_SESSION)
    suspend fun startSession(
        @Part("access_token") access_token: RequestBody?,
        @Part("booking_id") booking_id: RequestBody?,
        @Part("battery") battery: RequestBody?,
        @Part("promocode_id") promoCode_id: RequestBody?,
        @Part("ride_start_document1") ride_start_document1: MultipartBody.Part?,
        @Part("ride_start_document2") ride_start_document2: MultipartBody.Part?,
        @Part("ride_start_document3") ride_start_document3: MultipartBody.Part?
    ): Response<ResponseData<AddSessionResponse>>


    @POST(Constants.NOTIFICATION_UPDATE)
    suspend fun updateNotificationListing(
        @Body req: UpdateNotificationRequest
    ): Response<ResponseData<UpdateNotificationResponse>>

    @POST(Constants.NOTIFICATION_LISTING)
    suspend fun getNotificationListing(@Body request: NotificationRequest): Response<ResponseData<NotificationResponse>>

    @POST(Constants.RIDE_DETAILS)
    suspend fun getRideDetails(@Body request: RideDetailRequest): Response<ResponseData<RideDetailResponse>>

    @POST(Constants.PRINT_RECEIPT)
    suspend fun getPrintReceipt(@Body request: PrintReceiptRequest): Response<ResponseData<PrintReceiptResponse>>



}