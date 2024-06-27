package com.bitlasoft.trackingo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.TrankingoDialogPnrBinding
import com.bitlasoft.trackingo.databinding.TrialDialogUnnderneathLayoutBinding
import com.bitlasoft.trackingo.viewmodel.MapTrackingoViewModel

class MapTrackingoFragment : Fragment() {

    private var binding: TrialDialogUnnderneathLayoutBinding? = null
    private lateinit var viewModel: MapTrackingoViewModel
    private var apiKey: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TrialDialogUnnderneathLayoutBinding.inflate(inflater, container, false)
        apiKey = arguments?.getString("key")
        viewModel = ViewModelProvider(this).get(MapTrackingoViewModel::class.java)
        setupViews()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapTrackingoViewModel::class.java)

        if (!viewModel.isDialogShown()) {
            showDialog()
            viewModel.setDialogShown()
        }
    }

    private fun setupViews() {
        binding!!.feedbackButton1.setOnClickListener {
            findNavController().navigate(R.id.action_mapTrackingoFragment_to_feedbackFutureExpectationFragment)
            Bundle().apply {
                putString("key", apiKey)
            }
        }

        binding!!.feedbackButton2.setOnClickListener {
            findNavController().navigate(R.id.action_mapTrackingoFragment_to_feedbackFragment)
            Bundle().apply {
                putString("key", apiKey)
            }
        }

        binding!!.backToShortKey.setOnClickListener {
            findNavController().navigate(R.id.action_mapTrackingoFragment_to_shortKeyFragment)
        }
    }

    private fun showDialog() {
        val dialogBinding = TrankingoDialogPnrBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.CustomDialog)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.dialogButtonOK.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

