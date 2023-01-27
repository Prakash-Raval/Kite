package com.example.kite.profile.model


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)

data class ViewProfileRequest(
    @JsonProperty("access_token")
    var access_token: String? = "",
    @JsonProperty("third_party_id")
    var third_party_id: String? = "8b_2N2QV1"
)