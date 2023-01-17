package com.example.kite.otpverification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.otpverification.repository.OtpRepository

class OtpVMFactory(
    private val repository: OtpRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpViewModel::class.java)) {
            return OtpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}