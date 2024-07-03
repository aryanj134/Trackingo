package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ShortKeyUiBinding

class ShortKeyFragment : Fragment() {

    private var _binding: ShortKeyUiBinding? = null
    private val binding get() = _binding
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
                    findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment, Bundle().apply {
                        putString("shortKey", input.trim())
                        putString("type", "shortKey")
                    })
                }
                10 -> {
                    findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment, Bundle().apply {
                        putString("PnrNumber", input.trim())
                        putString("type", "pnr")
                    })
                }
                else -> {
                    findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment)
                }
            }
        }
    }
}