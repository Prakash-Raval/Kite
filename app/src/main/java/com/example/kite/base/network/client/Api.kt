package com.example.kite.base.network.client

import com.example.kite.base.network.model.ResponseData
import com.example.kite.constants.Constants
import com.example.kite.dateandtime.model.PromoCodeRequest
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.scheduletrip.model.*
import com.example.kite.viewscheduletrip.model.ViewTripRequest
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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
    suspend fun cancelScheduleTrip(@Body tripRequest: CancelTripRequest) : Response<ResponseData<CancelTripResponse>>

}