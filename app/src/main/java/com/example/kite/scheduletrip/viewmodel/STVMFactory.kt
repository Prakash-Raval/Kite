package com.example.kite.scheduletrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.scheduletrip.repository.ScheduleTripRepository

class STVMFactory(
    private val repository: ScheduleTripRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleTripViewModel::class.java)) {
            return ScheduleTripViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}