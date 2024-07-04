package com.bitlasoft.trackingo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackResponse
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponse
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackType1
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackType2
import com.bitlasoft.trackingo.domain.repository.FeedbackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackViewModel(private val feedbackRepository: FeedbackRepository) : ViewModel() {

    private val _feedback = MutableLiveData<FeedbackReviewResponse>()
    val feedback: LiveData<FeedbackReviewResponse> get() = _feedback

    private val _rating = MutableLiveData(0)
    val rating: LiveData<Int> get() = _rating

    private val _submitRatingsFeedback : MutableLiveData<FeedbackResponse> = MutableLiveData()
    val submitRatingsFeedback : LiveData<FeedbackResponse> get() = _submitRatingsFeedback

    private val _submitRatingFeedbackFutureExpectations : MutableLiveData<FeedbackResponse> = MutableLiveData()
    val submitRatingFeedbackFutureExpectations : LiveData<FeedbackResponse> get() = _submitRatingFeedbackFutureExpectations


    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun updateFeedback(feedbackResponse: FeedbackReviewResponse) {
        _feedback.value = feedbackResponse
    }

    fun updateRating(newRating: Int) {
        _rating.value = newRating
    }

    fun submitRatingsFeedback(shortKey: String?,feedbackType1: FeedbackType1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = feedbackRepository.submitRatingsFeedback(shortKey, feedbackType1)
                if (response.isSuccessful) {
                    _submitRatingsFeedback.postValue(response.body())
                    Log.d("FeedbackFragment", "Feedback submitted successfully: $feedbackType1")
//                    showToast("Feedback submitted successfully")
                } else {
//                    showToast("Failed to submit feedback: ${response.code()}")
                }
            } catch (e: Exception) {
//            showToast("Error submitting feedback: ${e.message}")
                Log.e("FeedbackFragment", "Error submitting feedback", e)
            }
        }
    }

    fun submitRatingFeedbackFutureExpectations(shortKey: String?, feedbackType2: FeedbackType2) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = feedbackRepository.submitRatingFeedbackFutureExpectations(shortKey, feedbackType2)
                if (response.isSuccessful) {
                    _submitRatingFeedbackFutureExpectations.postValue(response.body())
                    Log.d("FeedbackFragment", "Feedback submitted successfully: $feedbackType2")
//                    showToast("Feedback submitted successfully")
                } else {
//                    showToast("Failed to submit feedback: ${response.code()}")
                }
            } catch (e: Exception) {
//            showToast("Error submitting feedback: ${e.message}")
                Log.e("FeedbackFragment", "Error submitting feedback", e)
            }
        }
    }

}