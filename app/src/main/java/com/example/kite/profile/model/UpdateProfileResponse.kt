package com.example.kite.profile.model


import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateProfileResponse(
    @JsonProperty("access_token")
    var accessToken: String? = null,
    @JsonProperty("app_version")
    var appVersion: AppVersion? = null,
    @JsonProperty("city")
    var city: String? = null,
    @JsonProperty("country")
    var country: String? = null,
    @JsonProperty("country_code")
    var countryCode: String? = null,
    @JsonProperty("customer_address")
    var customerAddress: String? = null,
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
    @JsonProperty("device_type")
    var deviceType: String? = null,
    @JsonProperty("is_first_ride")
    var isFirstRide: Int? = null,
    @JsonProperty("is_verify")
    var isVerify: Int? = null,
    @JsonProperty("password")
    var password: String? = null,
    @JsonProperty("pin_code")
    var pinCode: Any? = null,
    @JsonProperty("preferred_language")
    var preferredLanguage: Int? = null,
    @JsonProperty("state")
    var state: String? = null,
    @JsonProperty("status")
    var status: Int? = null,
    @JsonProperty("zip_postal")
    var zipPostal: String? = null
) {
    data class AppVersion(
        @JsonProperty("critical")
        var critical: Int? = null,
        @JsonProperty("message")
        var message: String? = null,
        @JsonProperty("version_upgrade")
        var versionUpgrade: Int? = null
    )
}
