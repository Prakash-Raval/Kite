package com.example.kite.reservation.model


import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class ListReservationResponse(
    @JsonProperty("count") var count: Int? = null,
    @JsonProperty("reservation_data")
    var reservationData: List<ReservationData?>? = null
) : Parcelable {
    @Parcelize
    data class ReservationData(
        @JsonProperty("duration") var duration: String? = null,
        @JsonProperty("geo_location") var geoLocation: GeoLocation? = null,
        @JsonProperty("geolocation_id") var geolocationId: Int? = null,
        @JsonProperty("promocode_name") var promocodeName: String? = null,
        @JsonProperty("qr_code") var qrCode: String? = null,
        @JsonProperty("reservation_id") var reservationId: Int? = null,
        @JsonProperty("schedule_date") var scheduleDate: String? = null,
        @JsonProperty("schedule_full_date") var scheduleFullDate: String? = null,
        @JsonProperty("schedule_time") var scheduleTime: String? = null,
        @JsonProperty("status") var status: String? = null,
        @JsonProperty("third_party_name") var thirdPartyName: String? = null,
        @JsonProperty("vehicle_id") var vehicleId: Int? = null,
        @JsonProperty("vehicle_type") var vehicleType: String? = null,
        @JsonProperty("vehicle_type_image") var vehicleTypeImage: String? = null,
        @JsonProperty("vehicle_type_slug") var vehicleTypeSlug: String? = null
    ) : Parcelable {
        @Parcelize
        data class GeoLocation(
            @JsonProperty("lat") var lat: Double? = null,
            @JsonProperty("long") var long: Double? = null
        ) : Parcelable
    }
}
