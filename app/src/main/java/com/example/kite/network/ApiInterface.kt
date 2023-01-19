package com.example.kite.network

import com.example.kite.constants.Constants
import com.example.kite.login.model.LoginRequest
import com.example.kite.login.model.LoginResponse
import com.example.kite.otpverification.model.OtpRequest
import com.example.kite.otpverification.model.OtpResponse
import com.example.kite.program.model.ThirdPartyListRequest
import com.example.kite.program.model.ThirdPartyListResponse
import com.example.kite.signup.model.SignUpResponse2
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    /*  @Multipart
      @POST("customer/add_customer")
      suspend fun signUp(
          @Part("signup_type") signup_type: RequestBody?,
          @Part("fb_id") fb_id: RequestBody?,
          @Part("customer_first_name") customer_first_name: RequestBody?,
          @Part("customer_last_name") customer_last_name: RequestBody?,
          @Part("country_code") country_code: RequestBody?,
          @Part("password") password: RequestBody?,
          @Part("customer_phone_number") customer_phone_number: RequestBody?,
          @Part("country") country: RequestBody?,
          @Part("customer_profile_picture\"; filename=\"image.png") file: RequestBody?,
          @Part("customer_email") customer_email: RequestBody?,
          @Part("device_token") device_token: RequestBody?,
          @Part("device_type") device_type: RequestBody?,
          @Part("lang") lang: Int?
      ): Response<SignUpResponse2>
  */

    @POST(Constants.SIGNUP_URL)
    suspend fun setSignUp(@Body body: RequestBody): Response<SignUpResponse2>

    @POST(Constants.OTP_URL)
    suspend fun verifyOTP(@Body body: OtpRequest): Response<OtpResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun setLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST(Constants.THIRD_PARTY_LIST)
    suspend fun setList(@Body listRequest: ThirdPartyListRequest): Response<ThirdPartyListResponse>

}