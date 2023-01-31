package com.example.kite.network

import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.changepassword.model.ChangePasswordRequest
import com.example.kite.changepassword.model.ChangePasswordResponse
import com.example.kite.constants.Constants
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
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
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

    @POST(Constants.ADD_CARD)
    suspend fun addCard(@Body addCardRequest: AddCardRequest): Response<AddCardResponse>

    @POST(Constants.VIEW_PROFILE)
    suspend fun viewProfile(@Body viewProfileRequest: ViewProfileRequest): Response<ViewProfileResponse>

    @POST(Constants.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgetPasswordResponse>

    @POST(Constants.RESERVATION_LIST)
    suspend fun getReservationListing(@Body request: ListReservationRequest): Response<ListReservationResponse>

    @POST(Constants.CHANGE_PASSWORD)
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>

    @POST(Constants.BIKE_LISTING)
    suspend fun getBikeListing(@Body request: BikeListingRequest): Response<BikeListingResponse>
}