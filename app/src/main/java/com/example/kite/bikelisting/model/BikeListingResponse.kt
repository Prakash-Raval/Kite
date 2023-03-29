package com.example.kite.bikelisting.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class BikeListingResponse(
    @JsonProperty("gate_enabled")
    var gateEnabled: Int? = null,
    @JsonProperty("is_guest_user_trip_request")
    var isGuestUserTripRequest: List<Any?>? = null,
    @JsonProperty("property_address")
    var propertyAddress: String? = null,
    @JsonProperty("property_image")
    var propertyImage: String? = null,
    @JsonProperty("property_logo")
    var propertyLogo: String? = null,
    @JsonProperty("property_name")
    var propertyName: String? = null,
    @JsonProperty("trip_request_response")
    var tripRequestResponse: TripRequestResponse? = null,
    @JsonProperty("vehicle_details")
    var vehicleDetails: List<VehicleDetail?>? = null,
    @JsonProperty("wrongly_parked_vehicle_details")
    var wronglyParkedVehicleDetails: List<WronglyParkedVehicleDetail?>? = null
) {
    class TripRequestResponse

    data class VehicleDetail(
        @JsonProperty("address")
        var address: String? = null,
        @JsonProperty("after_ride_buffer_hour")
        var afterRideBufferHour: Int? = null,
        @JsonProperty("available_normal_vehicles")
        var availableNormalVehicles: Int? = null,
        @JsonProperty("available_vehicles")
        var availableVehicles: Int? = null,
        @JsonProperty("before_ride_buffer_hour")
        var beforeRideBufferHour: Int? = null,
        @JsonProperty("busy_vehicles")
        var busyVehicles: Int? = null,
        @JsonProperty("gate_enabled")
        var gateEnabled: Int? = null,
        @JsonProperty("geolocation")
        var geolocation: Geolocation? = null,
        @JsonProperty("geolocationDistance")
        var geolocationDistance: Double? = null,
        @JsonProperty("geolocation_id")
        var geolocationId: Int? = null,
        @JsonProperty("landmark")
        var landmark: String? = null,
        @JsonProperty("maintainace_vehicle")
        var maintainaceVehicle: Int? = null,
        @JsonProperty("manufacturer_id")
        var manufacturerId: Int? = null,
        @JsonProperty("marker_name")
        var markerName: String? = null,
        @JsonProperty("pricing_value")
        var pricingValue: Int? = null,
        @JsonProperty("repeat_days_count")
        var repeatDaysCount: Int? = null,
        @JsonProperty("reservation_status")
        var reservationStatus: Int? = null,
        @JsonProperty("vehicle_id")
        var vehicleId: Int? = null,
        @JsonProperty("vehicle_status")
        var vehicleStatus: Int? = null,
        @JsonProperty("vehicle_type")
        var vehicleType: String? = null,
        @JsonProperty("vehicle_type_id")
        var vehicleTypeId: Int? = null,
        @JsonProperty("vehicle_type_image")
        var vehicleTypeImage: String? = null,
        @JsonProperty("vehicle_type_slug")
        var vehicleTypeSlug: String? = null,
        @JsonProperty("working_vehicle")
        var workingVehicle: Int? = null,
        @JsonProperty("wrong_parked")
        var wrongParked: Int? = null
    ) {
        data class Geolocation(
            @JsonProperty("lat")
            var lat: Double? = null,
            @JsonProperty("long")
            var long: Double? = null
        )
    }

    data class WronglyParkedVehicleDetail(
        @JsonProperty("geolocation_id")
        var geolocationId: Int? = null,
        @JsonProperty("last_updated_point")
        var lastUpdatedPoint: LastUpdatedPoint? = null,
        @JsonProperty("wrong_parked")
        var wrongParked: Int? = null
    ) {
        data class LastUpdatedPoint(
            @JsonProperty("lat")
            var lat: Double? = null,
            @JsonProperty("long")
            var long: Double? = null
        )
    }
}
