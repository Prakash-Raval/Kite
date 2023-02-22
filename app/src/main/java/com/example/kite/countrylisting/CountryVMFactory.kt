package com.example.kite.countrylisting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CountryVMFactory(
    private val repository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}