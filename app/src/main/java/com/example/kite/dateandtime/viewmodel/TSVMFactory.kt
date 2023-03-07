package com.example.kite.dateandtime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.dateandtime.repository.TimeSlotRepository

class TSVMFactory(
    private val repository: TimeSlotRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeSlotViewModel::class.java)) {
            return TimeSlotViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}