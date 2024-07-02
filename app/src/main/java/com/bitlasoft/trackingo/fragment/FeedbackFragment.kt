package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.adapter.RatingAdapter
import com.bitlasoft.trackingo.databinding.FeedbackUpRequestLayoutBinding
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponse
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponseApi
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingItem
import com.bitlasoft.trackingo.utils.RatingClickListener
import com.bitlasoft.trackingo.utils.RetrofitInstanceFeedback
import com.bitlasoft.trackingo.viewModel.FeedbackViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class FeedbackFragment : Fragment(), RatingClickListener {

    private var _binding: FeedbackUpRequestLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RatingAdapter
    private val viewModel: FeedbackViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeedbackUpRequestLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val ratingItems = viewModel.titleRating.map { RatingItem(it, 0) }
        adapter = RatingAdapter(ratingItems, this)
        binding.recycleFeedbackRating.adapter = adapter
        binding.recycleFeedbackRating.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.feedback.observe(viewLifecycleOwner) { feedback ->
            feedback?.let {
                val position = viewModel.titleRating.indexOf(it.ratingTitle)
                if (position != -1) {
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.feedbackClose.setOnClickListener {
            findNavController().navigate(R.id.action_feedbackFragment_to_mapTrackingoFragment)
        }

        binding.feedbackSubmit.setOnClickListener {
            submitFeedback()
        }
    }

    override fun onRatingClicked(rating: Int, position: Int) {
        val ratingTitle = viewModel.titleRating[position]
        val feedback = FeedbackReviewResponse(ratingTitle, rating)
        viewModel.updateFeedback(feedback)
    }

    private fun submitFeedback() {
        val ratingsMap = adapter.getAllRatings()
        val suggestion = binding.feedbackWriteSuggestion.text.toString().trim()

        val feedbackReviewApi = FeedbackReviewResponseApi(ratingsMap, suggestion)

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstanceFeedback.apiInterface.submitFeedback(feedbackReviewApi)
                }

                if (response.isSuccessful) {
                    Log.d("FeedbackFragment", "Feedback submitted successfully: $feedbackReviewApi")
                    showToast("Feedback submitted successfully")
                } else {
                    showToast("Failed to submit feedback: ${response.code()}")
                }
            } catch (e: Exception) {
                showToast("Error submitting feedback: ${e.message}")
                Log.e("FeedbackFragment", "Error submitting feedback", e)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}


