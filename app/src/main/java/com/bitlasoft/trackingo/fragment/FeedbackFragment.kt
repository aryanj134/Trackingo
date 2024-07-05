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
        setupObserver()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val ratingItems = titleRating.map { RatingItem(it, 0) }
        adapter = RatingAdapter(ratingItems, this)
        binding.recycleFeedbackRating.adapter = adapter
        binding.recycleFeedbackRating.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun setupObserver() {
        feedbackViewModel.submitRatingsFeedback.observe(requireActivity()) {
            when(it.status) {
                200 -> showToast(it.message)
                else -> showToast("Failed to submit feedback. Please try again later.")
            }
            findNavController().popBackStack()
            adapter.clearAllRatings()
        }
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
        val rating1 = ratingsMap["Pickup Experience"]
        val rating2 = ratingsMap["Bus Quality"]
        val rating3 = ratingsMap["Staff behaviour"]
        val rating4 = ratingsMap["Amenities (as promised)"]
        val rating5 = ratingsMap["Dropping Experience"]
        val rating6 = ratingsMap["How likely you Recommend our Service?"]

        val feedbackType1 = FeedbackType1(type = 2, rating1, rating2, rating3, rating4, rating5, rating6, suggestion = suggestion)

        if(feedbackType1.amenities != null && feedbackType1.bus_quality != null && feedbackType1.drop_exp != null &&
            feedbackType1.pickup_exp != null && feedbackType1.recommend != null && feedbackType1.staff_behavior != null && feedbackType1.suggestion != "") {
            feedbackViewModel.submitRatingsFeedback(shortKey, feedbackType1)
        }else {
            showToast("Please enter details")
        }

    }

}


