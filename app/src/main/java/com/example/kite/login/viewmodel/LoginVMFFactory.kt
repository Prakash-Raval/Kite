package com.example.kite.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.login.repository.LoginRepository
import com.example.kite.signup.repository.SignUpRepository
import com.example.kite.signup.viewmodel.SignUpViewModel


class LoginVMFFactory(
    private val repository: LoginRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}

