package com.example.kite.profile.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.profile.model.UpdateProfileResponse
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ViewProfileRepository(private val api: Api) : BaseRepository() {

    suspend fun callViewProfile(request: ViewProfileRequest): ResponseHandler<ResponseData<ViewProfileResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.getViewProfile(request)
                })
        }
    }

    suspend fun callUpdateProfile(
        access_token: RequestBody?,
        customer_email: RequestBody?,
        customer_first_name: RequestBody?,
        customer_last_name: RequestBody?,
        city: RequestBody?,
        state: RequestBody?,
        country: RequestBody?,
        zip_postal: RequestBody?,
        customer_address: RequestBody?,
        is_first_ride: RequestBody?,
        customer_profile_picture: MultipartBody.Part
    ): ResponseHandler<ResponseData<UpdateProfileResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.updateProfile(
                        access_token,
                        customer_email,
                        customer_first_name,
                        customer_last_name,
                        city,
                        state,
                        country,
                        zip_postal,
                        customer_address,
                        is_first_ride,
                        customer_profile_picture
                    )
                })
        }
    }
}