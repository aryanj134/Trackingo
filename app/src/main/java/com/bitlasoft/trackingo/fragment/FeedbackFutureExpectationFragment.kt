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
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackType2
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
    private val feedbackViewModel: FeedbackViewModel by viewModel()
    var isPnr = false
    var shortKey = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FeedbackDownRequestLayoutBinding.inflate(inflater, container, false)

        shortKey = arguments?.getString("shortKey") ?: ""

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
        adapter.setRating(feedbackViewModel.rating.value ?: 0)
    }

    override fun onRatingClicked(rating: Int) {
        feedbackViewModel.updateRating(rating)
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

        val feedbackType2 = FeedbackType2(type = 2, ratingsMap["How do you rate Trackingo service?"], experienceText, expectationText)

        feedbackViewModel.submitRatingFeedbackFutureExpectations(shortKey, feedbackType2)

        feedbackViewModel.submitRatingFeedbackFutureExpectations.observe(requireActivity()) {
            when(it.status) {
                200 -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(requireContext(), "Failed to submit feedback. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }
        findNavController().popBackStack()
        adapter.clearAllRatings()

//                    } else {
//                        requireActivity().runOnUiThread {
//                            Toast.makeText(requireContext(), "Failed to submit feedback. Please try again later.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                requireActivity().runOnUiThread {
//                    Toast.makeText(requireContext(), "Unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}