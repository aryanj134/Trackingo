package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bitlasoft.trackingo.adapter.RatingFeedbackReviewAdapter
import com.bitlasoft.trackingo.databinding.FeedbackDownRequestLayoutBinding
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingFeedbackReviewResponse
import com.bitlasoft.trackingo.utils.FeedbackRatingClickListener
import com.bitlasoft.trackingo.utils.RetrofitInstanceFeedback
import com.bitlasoft.trackingo.viewModel.FeedbackViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class FeedbackFutureExpectationFragment : Fragment(), FeedbackRatingClickListener {

    private var _binding: FeedbackDownRequestLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RatingFeedbackReviewAdapter
    private val viewModel: FeedbackViewModel by viewModel()
    private var apiKey: String? = null
    var isPnr = false
    var shortKey = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeedbackDownRequestLayoutBinding.inflate(inflater, container, false)
//        apiKey = arguments?.getString("key")

        if (arguments!=null && arguments?.containsKey("type")==true ){
            isPnr = arguments?.getString("type").equals("pnr",false)
            if (isPnr){
                // later implementation
            } else{
                shortKey = arguments?.getString("shortKey")?:""
            }
        }

        onClickLClose()
        setupRecyclerView()
        setupSubmitButton()
        return binding.root
    }

    private fun onClickLClose() {
        binding.feedbackClose1.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        adapter = RatingFeedbackReviewAdapter(this)
        binding.recycleviewFeedback.adapter = adapter
        binding.recycleviewFeedback.layoutManager = GridLayoutManager(requireContext(), 1)
        //initial rating here
        adapter.setRating(viewModel.rating.value ?: 0)
    }

    override fun onRatingClicked(rating: Int) {
        viewModel.updateRating(rating)
        adapter.setRating(rating)
    }

    private fun setupSubmitButton() {
        binding.feedbackSubmit.setOnClickListener {
            submitFeedback()
        }
    }

    private fun submitFeedback() {
        val ratingsMap = adapter.getAllRatings()
        val experienceText = binding.feedbackExperienceWrite.text.toString().trim()
        val expectationText = binding.feedbackExpectWrite.text.toString().trim()

        val feedbackReview = RatingFeedbackReviewResponse(
            ratingsMap,
            experienceText,
            expectationText
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val key = shortKey
                key?.let {
                    val response = RetrofitInstanceFeedback.apiInterface.submitRatingFeedback(
                        key = it,
                        ratingFeedback = feedbackReview
                    )
                    if (response.isSuccessful) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        adapter.clearAllRatings()
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Failed to submit feedback. Please try again later.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } ?: run {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "API key is missing.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: HttpException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}