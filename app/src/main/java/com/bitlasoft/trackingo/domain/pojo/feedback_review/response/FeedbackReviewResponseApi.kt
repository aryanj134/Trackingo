package com.bitlasoft.trackingo.domain.pojo.feedback_review.response

data class FeedbackReviewResponseApi(
    val ratings: Map<String, Int>,
    val suggestion: String
)
