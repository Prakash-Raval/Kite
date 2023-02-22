package com.example.kite.countrylisting

import com.example.kite.network.ApiInterface
import retrofit2.Response

class CountryRepository(private val api: ApiInterface) {
    suspend fun getCountryList(): Response<CountryResponse> {
        return api.getCountryList()
    }
}