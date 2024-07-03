package com.bitlasoft.trackingo.data

import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponseApi
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingFeedbackReviewResponse
import com.bitlasoft.trackingo.domain.pojo.location_data.response.Coordinates
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LocationTimeDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("api/live/tracking_feedback")
    suspend fun submitRatingFeedback(
        @Query("key") key: String?,
        @Body ratingFeedback: RatingFeedbackReviewResponse
    ): Response<Unit>

    @POST("api/live/tracking_feedback")
    suspend fun submitFeedback(
        @Query("key") key: String?,
        @Body feedback: FeedbackReviewResponseApi
    ): Response<Unit>

    @GET("api/live/eta_map")
    suspend fun fetchDetails(
        @Query("current_status") currentStatus : Boolean?,
        @Query("key") short_key : String?
    ) : Response<LocationTimeDetails>

    @GET("api/live/journey_details")
    suspend fun fetchCoordinates(
        @Query("key") short_key : String?,
        @Query("zoom_position") position : Int?,
        @Query("platform") platform : String?
    ) : Response<Coordinates>

    @GET("api/tracking/panic_details")
    suspend fun panicResponse(
        @Query("short_code") short_key: String?,
        @Query("from_webApp") webApp: Boolean?
    )
}