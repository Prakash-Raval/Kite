package com.example.kite.signup.repository

import com.example.kite.network.ApiInterface
import com.example.kite.signup.model.SignUpResponse2
import okhttp3.RequestBody
import retrofit2.Response


class SignUpRepository(private val signUpService: ApiInterface) {
//    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse> {
//        return signUpService.signUp(signUpRequest)
//    }

    suspend fun setSignUp(multipartBody: RequestBody): Response<SignUpResponse2> {
        return signUpService.setSignUp(multipartBody)
    }


    /* suspend fun signUp(signUpRequest: SignUpRequest) : Response<SignUpResponse2>{
         return signUpService.signUp()
     }*/
}