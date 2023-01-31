package com.example.kite.reservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.reservation.repository.ListReservationRepository


class LRVMFactory(
    private val repository: ListReservationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListReservationViewModel::class.java)) {
            return ListReservationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}