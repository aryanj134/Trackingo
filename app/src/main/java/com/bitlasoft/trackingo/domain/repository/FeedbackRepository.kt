package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponseApi
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingFeedbackReviewResponse
import retrofit2.Response

class FeedbackRepository(private val apiInterface: ApiInterface) {

    suspend fun submitFeedback(feedbackReviewApi: FeedbackReviewResponseApi): Response<Unit> =
        apiInterface.submitFeedback(feedbackReviewApi)

    suspend fun submitRatingFeedback(apiKey: String, ratingFeedback: RatingFeedbackReviewResponse): Response<Unit> =
        apiInterface.submitRatingFeedback(apiKey, ratingFeedback)
}