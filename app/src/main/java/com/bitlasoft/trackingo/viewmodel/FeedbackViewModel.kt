package com.bitlasoft.trackingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponse
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponseApi
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingFeedbackReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

//import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackTitles
//
//class FeedbackViewModel : ViewModel() {
//
//    private val _feedback = MutableLiveData<FeedbackReviewResponse>()
//    val feedback: LiveData<FeedbackReviewResponse> get() = _feedback
//
//
//    private val _rating = MutableLiveData(0)
//    val rating: LiveData<Int> get() = _rating
//
//    val titleRating = listOf(
//        "Pickup Experience",
//        "Bus Quality",
//        "Staff behaviour",
//        "Amenities (as promised)",
//        "Dropping Experience",
//        "How likely you Recommend our Service?"
//    )
//
//    fun updateFeedback(feedbackResponse: FeedbackReviewResponse) {
//        _feedback.value = feedbackResponse
//    }
//
//    fun updateRating(newRating: Int) {
//        _rating.value = newRating
//    }
//
//    suspend fun submitFeedback(feedbackReviewApi: FeedbackReviewResponseApi): Response<Unit> =
//        withContext(Dispatchers.IO) {
//            apiInterfaceFeedback.submitFeedback(feedbackReviewApi)
//        }
//
//    suspend fun submitRatingFeedback(apiKey: String, ratingFeedback: RatingFeedbackReviewResponse): Response<Unit> =
//        withContext(Dispatchers.IO) {
//            apiInterfaceFeedbackRating.submitRatingFeedback(apiKey, ratingFeedback)
//        }
//    }
//
//
//}

class FeedbackViewModel : ViewModel() {

    private val _feedback = MutableLiveData<FeedbackReviewResponse>()
    val feedback: LiveData<FeedbackReviewResponse> get() = _feedback

    private val _rating = MutableLiveData(0)
    val rating: LiveData<Int> get() = _rating

    val titleRating = listOf(
        "Pickup Experience",
        "Bus Quality",
        "Staff behaviour",
        "Amenities (as promised)",
        "Dropping Experience",
        "How likely you Recommend our Service?"
    )

    fun updateFeedback(feedbackResponse: FeedbackReviewResponse) {
        _feedback.value = feedbackResponse
    }

    fun updateRating(newRating: Int) {
        _rating.value = newRating
    }

}