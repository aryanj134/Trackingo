package com.bitlasoft.trackingo.domain.pojo.feedback_review.response

import com.google.gson.annotations.SerializedName

data class FeedbackType2(
    @SerializedName("type")
    val type : Int,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("feedback")
    val experienceText: String?,
    @SerializedName("feature_request")
    val expectationText: String?
)