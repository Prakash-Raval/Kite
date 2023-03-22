package com.example.kite.network

import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.changepassword.model.ChangePasswordRequest
import com.example.kite.changepassword.model.ChangePasswordResponse
import com.example.kite.constants.Constants
import com.example.kite.countrylisting.CountryResponse
import com.example.kite.forgetpassword.model.ForgetPasswordResponse
import com.example.kite.forgetpassword.model.ForgotPasswordRequest
import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.model.OtpResponse
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import com.example.kite.signup.model.SignUpResponse2
import com.example.kite.statelisting.StateRequest
import com.example.kite.statelisting.StateResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiInterface {

    @POST(Constants.SIGNUP_URL)
    suspend fun setSignUp(@Body body: RequestBody): Response<SignUpResponse2>

    @POST(Constants.OTP_URL)
    suspend fun verifyOTP(@Body body: OtpRequest): Response<OtpResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun setLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST(Constants.THIRD_PARTY_LIST)
    suspend fun setList(@Body listRequest: ThirdPartyListRequest): Response<ThirdPartyListResponse>


    @POST(Constants.VIEW_PROFILE)
    suspend fun viewProfile(@Body viewProfileRequest: ViewProfileRequest): Response<ViewProfileResponse>

    @POST(Constants.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgetPasswordResponse>

    @POST(Constants.CHANGE_PASSWORD)
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>

    @POST(Constants.BIKE_LISTING)
    suspend fun getBikeListing(@Body request: BikeListingRequest): Response<BikeListingResponse>

    @GET(Constants.COUNTRY_LIST)
    suspend fun getCountryList(): Response<CountryResponse>

    @POST(Constants.STATE_LIST)
    suspend fun getStateList(@Body stateRequest: StateRequest): Response<StateResponse>

    @Headers("Content-Type: application/json")
    @POST(Constants.SCHEDULE_TRIP)
    suspend fun getScheduleTripData(@Body scheduleTripRequest: ScheduleTripRequest): Response<ScheduleTripResponse>


}