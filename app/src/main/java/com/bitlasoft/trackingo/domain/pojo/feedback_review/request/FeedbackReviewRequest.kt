package com.bitlasoft.trackingo.domain.pojo.feedback_review.request

import android.media.Rating

data class FeedbackReviewRequest(
    val title : String,
    val ratings: List<Int>
)