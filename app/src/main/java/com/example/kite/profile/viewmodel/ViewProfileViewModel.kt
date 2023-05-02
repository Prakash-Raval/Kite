package com.example.kite.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kite.base.ViewModelBase
import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.utils.Validation
import com.example.kite.profile.model.UpdateProfileResponse
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import com.example.kite.profile.repository.ViewProfileRepository
import com.example.kite.utils.PrefManager
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ViewProfileViewModel @Inject constructor() : ViewModelBase() {

    private var repository = ViewProfileRepository(ApiClient.getApiInterface())
    var responseDataProfile =
        MutableLiveData<ResponseHandler<ResponseData<ViewProfileResponse>?>>()

    var responseDataUpdateProfile =
        MutableLiveData<ResponseHandler<ResponseData<UpdateProfileResponse>?>>()
    private lateinit var viewProfile: ViewProfileResponse
    var token = ""
    fun getToken(token: String) {
        this.token = token
    }

    fun getViewProfileRequest(request: ViewProfileRequest) {
        viewModelScope.launch(coroutineContext) {
            responseDataProfile.value = ResponseHandler.Loading
            responseDataProfile.value = repository.callViewProfile(request)
        }
    }

    fun checkValidation() {
        viewProfile = (PrefManager.get("ViewProfileResponse") as ViewProfileResponse?)
            ?: ViewProfileResponse()
        when {
            !Validation.isNotNull(viewProfile.customerFirstName.toString().trim()) -> {
                showSnackBarMessage("Please enter first name")
            }
            !Validation.isNotNull(viewProfile.customerLastName.toString().trim()) -> {
                showSnackBarMessage("Please enter last name")
            }
            !Validation.isNotNull(viewProfile.customerEmail.toString().trim()) -> {
                showSnackBarMessage("Please enter email")
            }
            !Validation.isEmailValid(viewProfile.customerEmail.toString().trim()) -> {
                showSnackBarMessage("Please enter valid email")
            }
            !Validation.isNotNull(viewProfile.address.toString().trim()) -> {
                showSnackBarMessage("Please enter address")
            }
            !Validation.isNotNull(viewProfile.country.toString().trim()) -> {
                showSnackBarMessage("Please enter country")
            }
            !Validation.isNotNull(viewProfile.state.toString().trim()) -> {
                showSnackBarMessage("Please enter state")
            }
            !Validation.isNotNull(viewProfile.city.toString().trim()) -> {
                showSnackBarMessage("Please enter city")
            }
            !Validation.isNotNull(viewProfile.zipPostal.toString().trim()) -> {
                showSnackBarMessage("Please enter zip code")
            }
            else -> {

                val accessToken =
                    token.toRequestBody("text/plain".toMediaTypeOrNull())
                val firstName =
                    viewProfile.customerFirstName?.toRequestBody("text/plain".toMediaTypeOrNull())
                val lastName =
                    viewProfile.customerLastName?.toRequestBody("text/plain".toMediaTypeOrNull())
                val email =
                    viewProfile.customerEmail?.toRequestBody("text/plain".toMediaTypeOrNull())
                val country =
                    viewProfile.country?.toRequestBody("text/plain".toMediaTypeOrNull())
                val state =
                    viewProfile.state?.toRequestBody("text/plain".toMediaTypeOrNull())
                val city =
                    viewProfile.city?.toRequestBody("text/plain".toMediaTypeOrNull())
                val address =
                    viewProfile.customerAddress?.toRequestBody("text/plain".toMediaTypeOrNull())
                val zipCode =
                    viewProfile.zipPostal?.toRequestBody("text/plain".toMediaTypeOrNull())
                val isFirstRide =
                    viewProfile.isFirstRide?.toRequestBody("text/plain".toMediaTypeOrNull())

                val file = File("")
                val requestBody: RequestBody =
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart =
                    requestBody.let {
                        MultipartBody.Part.createFormData(
                            "profile_image",
                            file.name,
                            it
                        )
                    }

                getUpdateProfileRequest(
                    access_token = accessToken,
                    customer_first_name = firstName,
                    customer_last_name = lastName,
                    customer_address = address,
                    city = city,
                    state = state,
                    country = country,
                    customer_email = email,
                    zip_postal = zipCode,
                    customer_profile_picture = imagePart,
                    is_first_ride = isFirstRide
                )
            }
        }

    }


    private fun getUpdateProfileRequest(
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
    ) {
        viewModelScope.launch(coroutineContext) {
            responseDataUpdateProfile.value = ResponseHandler.Loading
            responseDataUpdateProfile.value = repository.callUpdateProfile(
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
        }
    }
}