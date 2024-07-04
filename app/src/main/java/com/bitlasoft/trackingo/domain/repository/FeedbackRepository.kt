package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackResponse
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackType1
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackType2
import retrofit2.Response

class FeedbackRepository(private val apiInterface: ApiInterface) {
    suspend fun submitRatingsFeedback(shortKey: String?,feedbackType1: FeedbackType1): Response<FeedbackResponse> =
        apiInterface.submitRatingsFeedback(shortKey, feedbackType1)

    suspend fun submitRatingFeedbackFutureExpectations(shortKey: String?, feedbackType2: FeedbackType2): Response<FeedbackResponse> =
        apiInterface.submitRatingFeedbackFutureExpectations(shortKey, feedbackType2)
}