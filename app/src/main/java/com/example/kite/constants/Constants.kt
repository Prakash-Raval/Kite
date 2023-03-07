package com.example.kite.constants


import com.example.kite.BuildConfig
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

    //COUNTRY LISTING
    const val COUNTRY_LIST = "country/country_list"
    const val STATE_LIST = "country/state_list"

    //schedule trip
    const val SCHEDULE_TRIP = "customer/view_vehicle_reservation_pricing_details"
    const val TIME_SLOT = "customer/get_reservation_time_slots"
    const val PROMO_CODE = "customer/validate_promo_code"

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


    //from base structure
    const val CAMERA_INTENT = 1001
    val ONBOARD: String = "ONBOARD"
    val IS_FIRST_TIME_LAUNCH: String = "on_board_screen"
    val MORE: String = "More"
    val LANGUAGE_CODE: String = "language_code"
    val CMS_TITLE: String = "cms-title"
    val CMS_URL: String = "cms-url"
    const val EN_CODE = "en"
    const val AR_CODE = "ar"
    const val SCREEN_TAG = "SCREEN_TAG"
   const val PREFERENCE_NAME = BuildConfig.APPLICATION_ID

    //    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    const val PAGE = 3
    const val FIRST_PAGE = 0

    const val MEDIA_TYPE_VIDEO = "video"
    const val MEDIA_TYPE_YOUTUBE = "youtube"

    const val mLocationInterval: Long = 2000
    const val mLocationFastestInterval: Long = 1000
    const val mLocationPriority: Int = 100
    const val USER_EXITS_YES = "yes"
    const val USER_EXITS_NO = "no"
    const val RESEND_OTP = "Yes"
    const val OTP = "otp"
    const val USER_DOESNT_EXIST = "USER_DOESNT_EXIST"
    const val USER_ACCOUNT_PENDING = "USER_ACCOUNT_PENDING"
    const val EMAIL_ID = "emailId"
    const val COME_FROM = "comeFrom"
    const val FORGOT_PASSWORD_SCREEN = "comeFromForgotPassWord"
    const val REGISTRATION_SCREEN = "comeFromRegistration"
    const val LOGIN_SCREEN = "comeFromLogin"
    const val SOCIAL_LOGIN = "yes"
    const val SOCIAL_ID = "socialId"
    const val COUNTRY = "country"
    const val SOCIAL_GOOGLE = "google"
    const val SOCIAL_FACEBOOK = "facebook"
    const val SOCIAL_GOOGLE_FACEBOOK = "googleOrFacebook"
    const val SOCIAL_LOGIN_DATA = "userSocialLoginData"

}

