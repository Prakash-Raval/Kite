package com.example.kite.changepassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.changepassword.repository.ChangePasswordRepository

class CPVMFactory(
    private val repository: ChangePasswordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            return ChangePasswordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}