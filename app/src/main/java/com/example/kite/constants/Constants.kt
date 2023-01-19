package com.example.kite.constants


import java.util.regex.Pattern

object Constants {
    const val BASE_URL = "https://kiteapi.demo.brainvire.dev/"
    const val LOGIN_URL = "customer/login_customer"
    const val SIGNUP_URL = "customer/add_customer"
    const val OTP_URL = "customer/customer_verify_otp"
    const val FORGOT_PASSWORD_URL = "customer/forgot_password_customer"
    const val THIRD_PARTY_LIST = "customer/list_third_parties"
    val PASSWORD_PATTERN: Pattern =
        Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,16}" +
                    "$"
        )
}

