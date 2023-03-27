package com.example.kite.scanqr.model


import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class ScanQRResponse(
    @JsonProperty("access_code")
    var accessCode: String? = null,
    @JsonProperty("accesscode_enable")
    var accesscodeEnable: Int? = null,
    @JsonProperty("booking_id")
    var bookingId: Int? = null,
    @JsonProperty("card_id")
    var cardId: Int? = null,
    @JsonProperty("card_number")
    var cardNumber: String? = null,
    @JsonProperty("card_type")
    var cardType: String? = null,
    @JsonProperty("category_id")
    var categoryId: Int? = null,
    @JsonProperty("currency_format")
    var currencyFormat: String? = null,
    @JsonProperty("futureReserved")
    var futureReserved: List<String?>? = null,
    @JsonProperty("is_lock_active")
    var isLockActive: Int? = null,
    @JsonProperty("mac_address")
    var macAddress: String? = null,
    @JsonProperty("mac_address_cycle")
    var macAddressCycle: String? = null,
    @JsonProperty("mac_address_lock")
    var macAddressLock: String? = null,
    @JsonProperty("pricing_value")
    var pricingValue: String? = null,
    @JsonProperty("refundable_deposit")
    var refundableDeposit: String? = null,
    @JsonProperty("vehicle_type")
    var vehicleType: String? = null,
    @JsonProperty("vehicle_type_id")
    var vehicleTypeId: Int? = null,
    @JsonProperty("vehicle_type_image")
    var vehicleTypeImage: String? = null,
    @JsonProperty("vehicle_type_slug")
    var vehicleTypeSlug: String? = null,
    @JsonProperty("wrong_drop_distance")
    var wrongDropDistance: Int? = null,
    @JsonProperty("wrong_drop_price")
    var wrongDropPrice: String? = null
) : Parcelable
