package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bitlasoft.trackingo.R
import androidx.navigation.fragment.findNavController
import com.bitlasoft.trackingo.databinding.MaptrackingoFragmentBinding
import com.bitlasoft.trackingo.databinding.ShortKeyUiBinding
import com.bitlasoft.trackingo.utils.Constants
import com.bitlasoft.trackingo.viewModel.LocationTimeViewModel
import com.bitlasoft.trackingo.viewModel.SharedViewModel

class ShortKeyFragment : Fragment() {

    private var _binding: ShortKeyUiBinding? = null
    private val binding get() = _binding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShortKeyUiBinding.inflate(inflater, container, false)

        setupButtonClick()

        return binding?.root
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupButtonClick() {
        _binding!!.buttonPnrNumber.setOnClickListener {
            val input = _binding!!.domainEditText.text.toString()
            if (input.isEmpty()) {
                showToast("Enter short key/PNR")
                return@setOnClickListener
            }
            when(input.length) {
                6 -> {
                    Constants.SHORT_KEY = input.trim()
                    sharedViewModel.setDialogShown(true)
                    findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment)
                }
                10 -> {
                    Constants.PNR_NUMBER = input.trim()
                    sharedViewModel.setDialogShown(true)
                    findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment)
                }
                else -> {
                    findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment)
                }
            }
        }
    }
}