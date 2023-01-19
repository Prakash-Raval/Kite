package com.example.kite.program.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.program.repository.ThirdPartyListRepository

class TPLViewModelFactory(
    private val repository: ThirdPartyListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdPartyListingViewModel::class.java)) {
            return ThirdPartyListingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}