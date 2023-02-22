package com.example.kite.statelisting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class StateVMFactory(
    private val repository: StateRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StateViewModel::class.java)) {
            return StateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}