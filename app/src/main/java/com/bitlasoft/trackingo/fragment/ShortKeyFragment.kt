package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.Toast
import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
import com.bitlasoft.trackingo.R
import androidx.navigation.fragment.findNavController
import com.bitlasoft.trackingo.databinding.ShortKeyUiBinding
//import com.bitlasoft.trackingo.utils.Constants
//import com.bitlasoft.trackingo.utils.ExtensiveFunctions
//import com.bitlasoft.trackingo.viewmodel.ShortKeyViewModel
//import org.koin.androidx.viewmodel.ext.android.viewModel


//class ShortKeyFragment : Fragment() {
//
//    private var _binding: ShortKeyUiBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: ShortKeyViewModel by viewModel()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ShortKeyUiBinding.inflate(inflater, container, false)
//        setupButtonClick()
//        observeViewModel()
//        return binding.root
//    }
//
//    private fun setupButtonClick() {
//        binding.buttonPnrNumber.setOnClickListener {
//            val input = binding.domainEditText.text.toString().trim()
//
//            if (input.isNotEmpty()) {
//                if (isValidShortKey(input)) {
//                    if (ExtensiveFunctions().isInternetAvailable(requireContext())) {
//                        viewModel.validateShortKeyOrPnr(input, "")
//                    } else {
//                        showToast("No internet connection")
//                    }
//                } else if (isValidPnrNumber(input)) {
//                    if (ExtensiveFunctions().isInternetAvailable(requireContext())) {
//                        viewModel.validateShortKeyOrPnr("", input)
//                    } else {
//                        showToast("No internet connection")
//                    }
//                } else {
//                    showToast("Invalid input")
//                }
//            } else {
//                showToast("Please enter a short key or PNR number")
//            }
//        }
//    }
//
//    private fun observeViewModel() {
//        viewModel.validationResponse.observe(viewLifecycleOwner, Observer { response ->
//            showToast(response)
//
//            if (response.equals("Validation successful", ignoreCase = true)) {
//                val input = binding.domainEditText.text.toString()
//                if (isValidShortKey(input)) {
//                    Constants.SHORT_KEY = input
//                } else if (isValidPnrNumber(input)) {
//                    Constants.PNR_NUMBER = input
//                }
//                findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment)
//            }
//        })
//    }
//
//    private fun isValidShortKey(input: String): Boolean {
//        return input.length == 6
//    }
//
//    private fun isValidPnrNumber(input: String): Boolean {
//        return input.length == 10
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}


class ShortKeyFragment : Fragment() {

    private var _binding: ShortKeyUiBinding? = null
    private val binding get() = _binding!!

    private val input = binding.domainEditText.text.toString().trim()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShortKeyUiBinding.inflate(inflater, container, false)
        setupButtonClick()

        return binding.root
    }

    private fun setupButtonClick() {
        binding.buttonPnrNumber.setOnClickListener {
            if (input == "1") {
                findNavController().navigate(
                    R.id.action_shortKeyFragment_to_secondFragment
                )
            } else {
                findNavController().navigate(
                    R.id.action_shortKeyFragment_to_secondFragment
                )
            }
        }
    }
}