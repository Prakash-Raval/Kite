package com.example.kite.signup.model


import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var data: Data? = null,
    @JsonProperty("message")
    var message: String? = null
) {
    data class Data(
        @JsonProperty("access_token")
        var accessToken: String? = null,
        @JsonProperty("country")
        var country: String? = null,
        @JsonProperty("country_code")
        var countryCode: String? = null,
        @JsonProperty("customer_email")
        var customerEmail: String? = null,
        @JsonProperty("customer_first_name")
        var customerFirstName: String? = null,
        @JsonProperty("customerFullName")
        var customerFullName: String? = null,
        @JsonProperty("customer_id")
        var customerId: Int? = null,
        @JsonProperty("customer_last_name")
        var customerLastName: String? = null,
        @JsonProperty("customer_phone_number")
        var customerPhoneNumber: String? = null,
        @JsonProperty("deviceToken")
        var deviceToken: String? = null,
        @JsonProperty("deviceType")
        var deviceType: String? = null,
        @JsonProperty("dynamic_message")
        var dynamicMessage: String? = null,
        @JsonProperty("is_first_ride")
        var isFirstRide: Int? = null,
        @JsonProperty("is_verify")
        var isVerify: Int? = null,
        @JsonProperty("otp_code")
        var otpCode: Int? = null,
        @JsonProperty("schedule_ride_message")
        var scheduleRideMessage: String? = null,
        @JsonProperty("schedule_title")
        var scheduleTitle: String? = null
    )
}