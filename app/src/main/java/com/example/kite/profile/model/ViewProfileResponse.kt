package com.example.kite.profile.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ViewProfileResponse(
        @JsonProperty("address")
        var address: Any? = null,
        @JsonProperty("city")
        var city: Any? = null,
        @JsonProperty("country")
        var country: String? = null,
        @JsonProperty("country_code")
        var countryCode: String? = null,
        @JsonProperty("customer_address")
        var customerAddress: Any? = null,
        @JsonProperty("customer_email")
        var customerEmail: String? = null,
        @JsonProperty("customer_first_name")
        var customerFirstName: String? = null,
        @JsonProperty("customer_id")
        var customerId: Int? = null,
        @JsonProperty("customer_last_name")
        var customerLastName: String? = null,
        @JsonProperty("customer_phone_number")
        var customerPhoneNumber: String? = null,
        @JsonProperty("customer_profile_picture")
        var customerProfilePicture: Any? = null,
        @JsonProperty("is_default_card")
        var isDefaultCard: Int? = null,
        @JsonProperty("is_first_ride")
        var isFirstRide: Int? = null,
        @JsonProperty("is_verify")
        var isVerify: Int? = null,
        @JsonProperty("is_verify_email")
        var isVerifyEmail: Int? = null,
        @JsonProperty("notification_updates")
        var notificationUpdates: Int? = null,
        @JsonProperty("password")
        var password: String? = null,
        @JsonProperty("pin_code")
        var pinCode: List<Any?>? = null,
        @JsonProperty("reservation_trips")
        var reservationTrips: Int? = null,
        @JsonProperty("schedule_ride_message")
        var scheduleRideMessage: String? = null,
        @JsonProperty("schedule_ride_message_french")
        var scheduleRideMessageFrench: String? = null,
        @JsonProperty("schedule_title")
        var scheduleTitle: String? = null,
        @JsonProperty("schedule_title_french")
        var scheduleTitleFrench: String? = null,
        @JsonProperty("state")
        var state: Any? = null,
        @JsonProperty("subscription")
        var subscription: Subscription? = null,
        @JsonProperty("trips_count")
        var tripsCount: Int? = null,
        @JsonProperty("trulioo_data")
        var truliooData: TruliooData? = null,
        @JsonProperty("zip_postal")
        var zipPostal: Any? = null
    ) {
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

        data class TruliooData(
            @JsonProperty("is_trulioo_verified")
            var isTruliooVerified: Int? = null,
            @JsonProperty("trulioo_response_fetched")
            var truliooResponseFetched: TruliooResponseFetched? = null,
            @JsonProperty("trulioo_status")
            var truliooStatus: String? = null
        ) {
            class TruliooResponseFetched
        }
    }
