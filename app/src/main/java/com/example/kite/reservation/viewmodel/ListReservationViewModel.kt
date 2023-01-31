package com.example.kite.reservation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.reservation.repository.ListReservationRepository
import kotlinx.coroutines.launch

class ListReservationViewModel(val repository: ListReservationRepository) : ViewModel() {
    private val reservationListMLD = MutableLiveData<ListReservationResponse>()
    val reservationListLD: LiveData<ListReservationResponse>
        get() = reservationListMLD

    fun getRequestData(request: ListReservationRequest) {
        call(request)
    }


    private fun call(request: ListReservationRequest) =
        viewModelScope.launch {
            try {
                val response = repository.getListReservation(request)
                if (response.isSuccessful) {
                    reservationListMLD.postValue(response.body())
                    Log.d("ReservationList", response.body().toString())
                } else {
                    Log.d("ReservationList", response.message())
                }

            } catch (e: Exception) {
                Log.d("ReservationList", e.message.toString())
            }
        }
}

