package com.example.kite.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.profile.repository.ViewProfileRepository


class ViewProfileVMFactory(
    private val repository: ViewProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewProfileViewModel::class.java)) {
            return ViewProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}