package com.example.kite.addcard.repository

import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.network.ApiInterface
import retrofit2.Response


class AddCardRepository(private val service: ApiInterface) {
    suspend fun addCard(request: AddCardRequest): Response<AddCardResponse> {
        return service.addCard(request)
    }
}