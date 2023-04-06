package com.example.kite.signup.repository

import com.example.kite.base.BaseRepository
import com.example.kite.base.network.client.Api
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.signup.model.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody


class SignUpRepository(private val api: Api) : BaseRepository() {
//    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse> {
//        return signUpService.signUp(signUpRequest)
//    }

    /* suspend fun setSignUp(multipartBody: RequestBody): Response<SignUpResponse> {
         return signUpService.setSignUp(multipartBody)
     }*/

    suspend fun callApiSignUp(request: RequestBody): ResponseHandler<ResponseData<SignUpResponse>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICall(
                call = {
                    api.setSignUp(request)
                })
        }
    }
    /* suspend fun signUp(signUpRequest: SignUpRequest) : Response<SignUpResponse2>{
         return signUpService.signUp()
     }*/
}