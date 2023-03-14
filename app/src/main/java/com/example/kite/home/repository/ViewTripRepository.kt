package com.example.kite.home.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewTripRepository(val api: Api) : BaseRepository() {

    suspend fun callApiViewTrip(request: ListReservationRequest):
            ResponseHandler<ResponseData<ListReservationResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.viewScheduleTrip(request)
                })
        }
    }

}