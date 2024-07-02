package com.bitlasoft.trackingo.domain.pojo.feedback_review.request

data class FeedbackReviewRequest(
    val title : String,
    val ratings: List<Int>
)