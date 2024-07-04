package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bitlasoft.trackingo.adapter.RatingAdapter
import com.bitlasoft.trackingo.databinding.FeedbackUpRequestLayoutBinding
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackReviewResponse
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackType1
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.RatingItem
import com.bitlasoft.trackingo.utils.RatingClickListener
import com.bitlasoft.trackingo.viewModel.FeedbackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FeedbackFragment : Fragment(), RatingClickListener {

    private var _binding: FeedbackUpRequestLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RatingAdapter
    private val feedbackViewModel: FeedbackViewModel by viewModel()
    var shortKey = ""
    val titleRating = listOf(
        "Pickup Experience",
        "Bus Quality",
        "Staff behaviour",
        "Amenities (as promised)",
        "Dropping Experience",
        "How likely you Recommend our Service?"
    )

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

        shortKey = arguments?.getString("shortKey") ?: ""

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val ratingItems = titleRating.map { RatingItem(it, 0) }
        adapter = RatingAdapter(ratingItems, this)
        binding.recycleFeedbackRating.adapter = adapter
        binding.recycleFeedbackRating.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        feedbackViewModel.feedback.observe(viewLifecycleOwner) { feedback ->
            feedback?.let {
                val position = titleRating.indexOf(it.ratingTitle)
                if (position != -1) {
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.feedbackClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.feedbackSubmit.setOnClickListener {
            submitFeedback()
            feedbackViewModel.submitRatingsFeedback.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                adapter.clearAllRatings()
            }
        }
    }

    override fun onRatingClicked(rating: Int, position: Int) {
        val ratingTitle = titleRating[position]
        val feedback = FeedbackReviewResponse(ratingTitle, rating)
        feedbackViewModel.updateFeedback(feedback)
    }

    private fun submitFeedback() {
        val ratingsMap = adapter.getAllRatings()
        val suggestion = binding.feedbackWriteSuggestion.text.toString().trim()

        val feedbackType1 = FeedbackType1(type = 2, ratingsMap["Pickup Experience"], ratingsMap["Bus Quality"],
            ratingsMap["Staff behaviour"], ratingsMap["Amenities (as promised)"], ratingsMap["Dropping Experience"],
            ratingsMap["How likely you Recommend our Service?"], suggestion = suggestion)

        feedbackViewModel.submitRatingsFeedback(shortKey, feedbackType1)

        feedbackViewModel.submitRatingsFeedback.observe(requireActivity()) {
            when(it.status) {
                200 -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(requireContext(), "Failed to submit feedback. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        findNavController().popBackStack()
        adapter.clearAllRatings()
    }

}


