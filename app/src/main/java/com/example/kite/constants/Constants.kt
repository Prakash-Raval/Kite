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

    //EXTRAS
    const val REQUEST_IMAGE_CAPTURE = 1
}

