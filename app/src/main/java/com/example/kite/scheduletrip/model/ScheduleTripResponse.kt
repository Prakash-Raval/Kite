package com.example.kite.scheduletrip.model


import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize


@Parcelize
data class ScheduleTripResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var `data`: Data? = null,
    @JsonProperty("message")
    var message: String? = null
) : Parcelable {

    @Parcelize
    data class Data(
        @JsonProperty("cancellation_charge")
        var cancellationCharge: String? = null,
        @JsonProperty("company_name")
        var companyName: String? = null,
        @JsonProperty("currency_format")
        var currencyFormat: String? = null,
        @JsonProperty("maintenance_charges")
        var maintenanceCharges: String? = null,
        @JsonProperty("manufacturer_id")
        var manufacturerId: Int? = null,
        @JsonProperty("modal_name")
        var modalName: String? = null,
        @JsonProperty("repeat_count")
        var repeatCount: Int? = null,
        @JsonProperty("security_deposit")
        var securityDeposit: String? = null,
        @JsonProperty("subscribed_user_cancellation_charge")
        var subscribedUserCancellationCharge: String? = null,
        @JsonProperty("subscribed_user_update_cancel_hour_limit")
        var subscribedUserUpdateCancelHourLimit: Int? = null,
        @JsonProperty("subscribed_user_update_count")
        var subscribedUserUpdateCount: Int? = null,
        @JsonProperty("subscribed_user_updation_charge")
        var subscribedUserUpdationCharge: String? = null,
        @JsonProperty("third_party_id")
        var thirdPartyId: String? = null,
        @JsonProperty("timezone_arr")
        var timezoneArr: List<TimezoneArr?>? = null,
        @JsonProperty("trip_duration")
        var tripDuration: List<TripDuration?>? = null,
        @JsonProperty("update_cancel_hour_limit")
        var updateCancelHourLimit: Int? = null,
        @JsonProperty("update_count")
        var updateCount: Int? = null,
        @JsonProperty("updation_charge")
        var updationCharge: String? = null,
        @JsonProperty("vehicle_reservation_pricing_id")
        var vehicleReservationPricingId: Int? = null,
        @JsonProperty("vehicle_type")
        var vehicleType: String? = null,
        @JsonProperty("vehicle_type_id")
        var vehicleTypeId: Int? = null,
        @JsonProperty("vehicle_type_image")
        var vehicleTypeImage: String? = null,
        @JsonProperty("wrong_drop_charges")
        var wrongDropCharges: String? = null,
        @JsonProperty("wrong_drop_distance")
        var wrongDropDistance: String? = null
    ) : Parcelable {
        @Parcelize
        data class TimezoneArr(
            @JsonProperty("time_zone")
            var timeZone: String? = null,
            @JsonProperty("title")
            var title: String? = null
        ) : Parcelable

        @Parcelize
        data class TripDuration(
            @JsonProperty("duration")
            var duration: String? = null,
            @JsonProperty("image")
            var image: String? = null,
            @JsonProperty("price")
            var price: String? = null,
            @JsonProperty("title")
            var title: String? = null
        ) : Parcelable
    }

}