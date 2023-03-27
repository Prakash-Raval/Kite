package com.example.kite.constants


import java.util.regex.Pattern

object Constants {


    //URLS
    const val RENTAL_AGREEMENT: String =
        "https://admin.kitemobilitydev.com/#/release-agreement/1Gj0mr5eI?lang=english"
    const val TERMS_URL: String = "https://admin.kitemobilitydev.com/#/terms-condition?lang=english"
    const val POLICY_URL: String = "https://admin.kitemobilitydev.com/#/privacy-policy?lang=english"
    const val SUPPORT_URL: String = "https://www.kitemobility.io/support?lang=eng"
    const val BASE_URL = "https://api.kitemobilitydev.com"

    //CUSTOMER PROFILE
    const val LOGIN_URL = "customer/login_customer"
    const val SIGNUP_URL = "customer/add_customer"
    const val OTP_URL = "customer/customer_verify_otp"
    const val FORGOT_PASSWORD_URL = "customer/forgot_password_customer"
    const val VIEW_PROFILE = "customer/view-profile"
    const val CHANGE_PASSWORD = "customer/customer_reset_password"

    //THIRD PARTY LISTING
    const val THIRD_PARTY_LIST = "customer/list_third_parties"

    //customer listing
    const val RESERVATION_LIST = "customer/list_reservations"
    const val BIKE_LISTING = "customer/list_bikes"

    //CUSTOMER CARD DETAILS
    const val ADD_CARD = "payment/add_card"
    const val GET_CARD = "payment/get_card"

    //COUNTRY LISTING
    const val COUNTRY_LIST = "country/country_list"
    const val STATE_LIST = "country/state_list"

    //schedule trip
    const val SCHEDULE_TRIP = "customer/view_vehicle_reservation_pricing_details"
    const val TIME_SLOT = "customer/get_reservation_time_slots"
    const val PROMO_CODE = "customer/validate_promo_code"
    const val ADD_SCHEDULE_TRIP = "customer/add_vehicle_reservation"
    const val VIEW_SCHEDULE_TRIP = "customer/view_reservation_details"
    const val UPDATE_SCHEDULE_TRIP = "customer/edit_vehicle_reservation_details"
    const val CANCEL_SCHEDULE_TRIP = "customer/cancel_vehicle_reservation"

    //REGEX
    val VISA: Pattern = Pattern.compile("^4[0-9]{6,}\$")
    val MASTER: Pattern = Pattern.compile("^5[1-5][0-9]{5,}\$")
    val PASSWORD_PATTERN: Pattern =
        Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,16}" +
                    "$"
        )

    //qr code
    const val SCAN_QR = "customer/scan_qrcode"

    //Ride History
    const val RIDE_HISTORY = "customer/ride_history"


    //subscription
    const val ADD_SUBSCRIPTION = "subscription/add_subscription"
    const val CANCEL_SUBSCRIPTION = "subscription/cancel_subscription"

    //notification
    const val NOTIFICATION_LISTING = "customer/notification_listing"
    const val NOTIFICATION_UPDATE = "customer/update_notification"

    //vehicle type
    const val CAR = "eCar"
    const val BIKE = "eBike"
    const val SCOOTER = "eScooter"


    //starting ride session
    const val START_SESSION = "/customer/session_started"

    //ride details
    const val RIDE_DETAILS = "customer/booking_details"

    //print receipt
    const val PRINT_RECEIPT = "customer/print-transaction-details"


}

