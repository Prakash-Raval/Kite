package com.example.kite.network

import com.example.kite.changepassword.model.ChangePasswordRequest
import com.example.kite.changepassword.model.ChangePasswordResponse
import com.example.kite.constants.Constants
import com.example.kite.forgetpassword.model.ForgetPasswordResponse
import com.example.kite.forgetpassword.model.ForgotPasswordRequest
import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.model.OtpResponse
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import com.example.kite.signup.model.SignUpResponse2
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiInterface {

    @POST(Constants.SIGNUP_URL)
    suspend fun setSignUp(@Body body: RequestBody): Response<SignUpResponse2>

    @POST(Constants.OTP_URL)
    suspend fun verifyOTP(@Body body: OtpRequest): Response<OtpResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun setLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST(Constants.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgetPasswordResponse>

    @POST(Constants.CHANGE_PASSWORD)
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>


    @Headers("Content-Type: application/json")
    @POST(Constants.SCHEDULE_TRIP)
    suspend fun getScheduleTripData(@Body scheduleTripRequest: ScheduleTripRequest): Response<ScheduleTripResponse>


}