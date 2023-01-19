package com.example.kite.program.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ThirdPartyListResponse(
    @JsonProperty("code")
    var code: Int? = null,
    @JsonProperty("data")
    var data: List<Data?>? = null,
    @JsonProperty("error_message")
    var errorMessage: String? = null,
    @JsonProperty("message")
    var message: String? = null
) {
    data class Data(
        @JsonProperty("access_code")
        var accessCode: String? = null,
        @JsonProperty("accesscode_enable")
        var accesscodeEnable: Int? = null,
        @JsonProperty("agreement1")
        var agreement1: String? = null,
        @JsonProperty("agreement2")
        var agreement2: String? = null,
        @JsonProperty("agreement_data")
        var agreementData: Any? = null,
        @JsonProperty("agreement_enable")
        var agreementEnable: Int? = null,
        @JsonProperty("release_agreement")
        var releaseAgreement: List<String?>? = null,
        @JsonProperty("release_agreement1")
        var releaseAgreement1: List<String?>? = null,
        @JsonProperty("third_party_address")
        var thirdPartyAddress: String? = null,
        @JsonProperty("third_party_id")
        var thirdPartyId: String? = null,
        @JsonProperty("third_party_image")
        var thirdPartyImage: String? = null,
        @JsonProperty("third_party_logo")
        var thirdPartyLogo: String? = null,
        @JsonProperty("third_party_name")
        var thirdPartyName: String? = null,
        @JsonProperty("third_party_type")
        var thirdPartyType: Int? = null
    )
}