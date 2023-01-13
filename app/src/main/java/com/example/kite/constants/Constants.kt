package com.example.kite.constants


import java.util.regex.Pattern

object Constants {
    const val BASE_URL = "https://kiteapi.demo.brainvire.dev/"
    const val LOGIN_URL = "customer/login_customer"
    const val SIGNUP_URL = "customer/add_customer"
    const val OTP_URL = "customer/customer_verify_otp"
    const val FORGOT_PASSWORD_URL = "customer/forgot_password_customer"
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

/*
* {"code":200,"message":"Registration successful","data":{
* "access_token":"$2a$10$0TW8HwYdwPQMgwr9bXgu2ujx2FyHqNQxK1ZsyV3byApWf3GwpHzhO",
* "customer_id":22,
* "customer_phone_number":"1234567890",
* "country_code":"+91",
* "customer_first_name":"Prakash",
* "customer_last_name":"Raval",
* "customerFullName":"Prakash Raval",
* "customer_email":"prakash.raval12345@yopmail.com",
* "deviceType":"2",
* "deviceToken":"1234567893",
* "otp_code":2335,
* "is_verify":0,
* "country":"Canada",
* "is_first_ride":1,
* "schedule_title":"Scheduling your next trip",
* "schedule_ride_message":"You can now book your next trip ahead of your desired timeframe. Select your vehicle, and choose Schedule Trip if available.",
* "schedule_title_french":null,
* "schedule_ride_message_french":null,
* "subscription":{"is_subscribe":0,"subscription_start_date":"","subscription_end_date":"","is_subscription_autorenewal":0,"subscription_name":"Kite Runner","subscription_price":4.99},"is_default_card":0,"dynamic_message":"As a guest you have to pro
* vide the resident information to access the vehicles. Your pin will be generated after resident's approval."}}
* */