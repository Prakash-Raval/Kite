package com.example.kite.dateandtime.model

import com.fasterxml.jackson.annotation.JsonProperty


data class PromoCodeResponse(
    @JsonProperty("code_id")
    var codeId: Int? = 0,
    @JsonProperty("promocode_type")
    var promocodeType: Int? = null,
    @JsonProperty("status")
    var status: Int? = null,
    @JsonProperty("created_on")
    var createdOn: String? = null,
    @JsonProperty("expiry")
    var expiry: String? = null,
    @JsonProperty("promocode_value")
    var promocodeValue: String? = null
)
