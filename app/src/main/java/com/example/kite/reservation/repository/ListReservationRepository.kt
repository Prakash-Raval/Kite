package com.example.kite.reservation.repository

import com.example.kite.network.ApiInterface
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import retrofit2.Response

class ListReservationRepository(val api: ApiInterface) {

    suspend fun getListReservation(request: ListReservationRequest): Response<ListReservationResponse> {
        return api.getReservationListing(request)
    }
}