package com.example.kite.viewscheduletrip.model


import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class ViewTripResponse(
    @JsonProperty("cancel_fee")
    var cancelFee: String? = null,
    @JsonProperty("cancellation_hour")
    var cancellationHour: Int? = null,
    @JsonProperty("currency_format")
    var currencyFormat: String? = null,
    @JsonProperty("duration")
    var duration: String? = null,
    @JsonProperty("geo_location")
    var geoLocation: GeoLocation? = null,
    @JsonProperty("geolocation_id")
    var geolocationId: Int? = null,
    @JsonProperty("hour_image")
    var hourImage: String? = null,
    @JsonProperty("is_subscribed")
    var isSubscribed: Int? = null,
    @JsonProperty("manufacturer_id")
    var manufacturerId: Int? = null,
    @JsonProperty("promocode_name")
    var promocodeName: String? = null,
    @JsonProperty("qr_code")
    var qrCode: String? = null,
    @JsonProperty("recurring_day_count")
    var recurringDayCount: Int? = null,
    @JsonProperty("repeat_count")
    var repeatCount: Int? = null,
    @JsonProperty("reservation_id")
    var reservationId: Int? = null,
    @JsonProperty("reservation_updated_count")
    var reservationUpdatedCount: Int? = null,
    @JsonProperty("schedule_date")
    var scheduleDate: String? = null,
    @JsonProperty("schedule_full_date")
    var scheduleFullDate: String? = null,
    @JsonProperty("schedule_time")
    var scheduleTime: String? = null,
    @JsonProperty("subscribed_user_cancel_fee")
    var subscribedUserCancelFee: String? = null,
    @JsonProperty("subscribed_user_cancellation_hour")
    var subscribedUserCancellationHour: Int? = null,
    @JsonProperty("subscribed_user_update_charge")
    var subscribedUserUpdateCharge: String? = null,
    @JsonProperty("subscribed_user_update_hour")
    var subscribedUserUpdateHour: Int? = null,
    @JsonProperty("subscribed_user_update_trip_count")
    var subscribedUserUpdateTripCount: Int? = null,
    @JsonProperty("third_party_name")
    var thirdPartyName: String? = null,
    @JsonProperty("time_zone")
    var timeZone: String? = null,
    @JsonProperty("timezone_arr")
    var timezoneArr: List<TimezoneArr?>? = null,
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("trip_duration")
    var tripDuration: List<TripDuration?>? = null,
    @JsonProperty("update_charge")
    var updateCharge: String? = null,
    @JsonProperty("update_hour")
    var updateHour: Int? = null,
    @JsonProperty("update_trip_count")
    var updateTripCount: Int? = null,
    @JsonProperty("vehicle_id")
    var vehicleId: Int? = null,
    @JsonProperty("vehicle_type")
    var vehicleType: String? = null,
    @JsonProperty("vehicle_type_id")
    var vehicleTypeId: Int? = null,
    @JsonProperty("vehicle_type_image")
    var vehicleTypeImage: String? = null,
    @JsonProperty("vehicle_type_slug")
    var vehicleTypeSlug: String? = null
) : Parcelable {

    @Parcelize
    data class GeoLocation(
        @JsonProperty("lat")
        var lat: Double? = null,
        @JsonProperty("long")
        var long: Double? = null
    ) : Parcelable

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
