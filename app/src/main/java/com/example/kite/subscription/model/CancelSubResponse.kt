package com.example.kite.subscription.model


import com.fasterxml.jackson.annotation.JsonProperty

data class CancelSubResponse(
    @JsonProperty("subscription")
    var subscription: Subscription? = null
) {
    data class Subscription(
        @JsonProperty("is_subscribe")
        var isSubscribe: Int? = null,
        @JsonProperty("is_subscription_autorenewal")
        var isSubscriptionAutorenewal: Int? = null,
        @JsonProperty("subscription_end_date")
        var subscriptionEndDate: String? = null,
        @JsonProperty("subscription_name")
        var subscriptionName: String? = null,
        @JsonProperty("subscription_price")
        var subscriptionPrice: Double? = null,
        @JsonProperty("subscription_start_date")
        var subscriptionStartDate: String? = null
    )
}
