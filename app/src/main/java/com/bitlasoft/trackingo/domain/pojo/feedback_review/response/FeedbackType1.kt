package com.bitlasoft.trackingo.domain.pojo.feedback_review.response

import com.google.gson.annotations.SerializedName

data class FeedbackType1(
    @SerializedName("type")
    val type: Int,
    @SerializedName("pickup_exp")
    val pickup_exp: Int?,
    @SerializedName("bus_quality")
    val bus_quality: Int?,
    @SerializedName("staff_behavior")
    val staff_behavior: Int?,
    @SerializedName("amenities")
    val amenities: Int?,
    @SerializedName("recommend")
    val recommend: Int?,
    @SerializedName("drop_exp")
    val drop_exp: Int?,
    @SerializedName("feedback")
    val suggestion: String
)
