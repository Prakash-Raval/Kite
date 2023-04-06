package com.example.kite.signup.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SignUpResponse(
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
        @JsonProperty("is_default_card")
        var isDefaultCard: Int? = null,
        @JsonProperty("is_first_ride")
        var isFirstRide: Int? = null,
        @JsonProperty("is_verify")
        var isVerify: Int? = null,
        @JsonProperty("otp_code")
        var otpCode: Int? = null,
        @JsonProperty("schedule_ride_message")
        var scheduleRideMessage: String? = null,
        @JsonProperty("schedule_ride_message_french")
        var scheduleRideMessageFrench: Any? = null,
        @JsonProperty("schedule_title")
        var scheduleTitle: String? = null,
        @JsonProperty("schedule_title_french")
        var scheduleTitleFrench: Any? = null,
        @JsonProperty("subscription")
        var subscription: Subscription? = null
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Subscription(
            @JsonProperty("is_subscribe")
            var isSubscribe: Int? = null,
            @JsonProperty("is_subscription_autorenewal")
            var isSubscriptionAutorenewal: Int? = null,
            @JsonProperty("subscription_end_date")
            var subscriptionEndDate: String? = null,
            @JsonProperty("subscription_name")
            var subscriptionName: String? = null,
            @JsonProperty("subscription_price")
            var subscriptionPrice: Double? = null,
            @JsonProperty("subscription_start_date")
            var subscriptionStartDate: String? = null
        )
    }
