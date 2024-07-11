package com.bitlasoft.trackingo.domain.pojo.feedback_review.response

import com.google.gson.annotations.SerializedName

data class FeedbackResponse(
        @SerializedName("status")
        val status: Int?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("error_message")
        val error_message: String?
)