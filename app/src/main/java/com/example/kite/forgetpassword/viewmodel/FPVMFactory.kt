package com.example.kite.forgetpassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.forgetpassword.repository.ForgotPasswordRepository

class FPVMFactory(
    private val repository: ForgotPasswordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            return ForgotPasswordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}