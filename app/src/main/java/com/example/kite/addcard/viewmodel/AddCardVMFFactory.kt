package com.example.kite.addcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kite.addcard.repository.AddCardRepository


class AddCardVMFFactory(
    private val repository: AddCardRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            return AddCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}