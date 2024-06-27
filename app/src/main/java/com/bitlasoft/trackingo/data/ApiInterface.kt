package com.bitlasoft.trackingo.data

import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponseApi
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingFeedbackReviewResponse
import com.bitlasoft.trackingo.domain.pojo.short_key_pnr.request.ShortKeyValidationRequest
import com.bitlasoft.trackingo.domain.pojo.short_key_pnr.response.ShortKeyValidationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
//
//    @GET("validateShortKey")
//    suspend fun validateShortKey(@Query("key") key: String): ApiResponse<Boolean>

    @POST("v3/b078a1b3-308e-4cc5-9fa8-05041f6328f1")
    suspend fun validateShortKeyApi(
        @Body request: ShortKeyValidationRequest
    ): Response<ShortKeyValidationResponse>

    @POST("api/live/tracking_feedback")
    suspend fun submitRatingFeedback(
        @Query("key") key: String,
        @Body ratingFeedback: RatingFeedbackReviewResponse
    ): Response<Unit>

    @POST("feedback/submit")
    suspend fun submitFeedback(
        @Body feedback: FeedbackReviewResponseApi
    ): Response<Unit>

}