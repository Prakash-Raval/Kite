package com.example.kite.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.signup.repository.SignUpRepository


class SignUpVMFFactory(
    private val repository: SignUpRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}