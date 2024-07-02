package com.bitlasoft.trackingo.domain.pojo.feedback_review.response

data class RatingFeedbackReviewResponse(
    val ratings: Map<String, Int>,
    val experienceText: String,
    val expectationText: String
)
