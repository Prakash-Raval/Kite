package com.example.kite.bikelisting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.bikelisting.repository.BikeListingRepository

class BLVMFactory(
    private val repository: BikeListingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BikeListingViewModel::class.java)) {
            return BikeListingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}