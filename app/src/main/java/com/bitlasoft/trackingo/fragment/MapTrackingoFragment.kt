package com.bitlasoft.trackingo.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.adapter.BpDpAdapter
import com.bitlasoft.trackingo.databinding.MaptrackingoFragmentBinding
import com.bitlasoft.trackingo.databinding.TrankingoDialogPnrBinding
import com.bitlasoft.trackingo.utils.Constants
import com.bitlasoft.trackingo.viewModel.CoordinatesViewModel
import com.bitlasoft.trackingo.viewModel.LocationTimeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bitlasoft.trackingo.viewModel.SharedViewModel

class MapTrackingoFragment: Fragment() {
    private lateinit var _binding: MaptrackingoFragmentBinding
//    private val binding get() = _binding!!
    private val locTimeViewModel: LocationTimeViewModel by viewModel()
    private val coordinatesViewModel : CoordinatesViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var googleMap: GoogleMap
    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflating the layout
        if(this::_binding.isInitialized.not()) {
            _binding = MaptrackingoFragmentBinding.inflate(inflater, container, false)

            //Setting the recyclerView layout as Horizontal
            val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            _binding.recyclerView.layoutManager = horizontalLayoutManager

            // Fetch Data
            locTimeViewModel.getLocationTimeDetails(true, Constants.SHORT_KEY)
            mapView = _binding.mapLayoutView
            mapView?.onCreate(savedInstanceState)
            mapView?.getMapAsync { map ->
                googleMap = map

                coordinatesViewModel.getCoordinates(Constants.SHORT_KEY, 12, "mobile")
//                googleMap.uiSettings.isZoomControlsEnabled = true
//                googleMap.uiSettings.isCompassEnabled = true
//                googleMap.uiSettings.isZoomGesturesEnabled = true
//                googleMap.uiSettings.isMapToolbarEnabled = false
//                googleMap.uiSettings.isScrollGesturesEnabled = true
                googleMap.uiSettings.apply {
                    isZoomControlsEnabled = true
                    isCompassEnabled = true
                    isZoomGesturesEnabled = true
                    isMapToolbarEnabled = false
                    isScrollGesturesEnabled = true
                }
            }
        }
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back Button to ShortKeyFragment
        _binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mapTrackingoFragment_to_shortKeyFragment)
        }

        setUpButtonClick()

        textSpan()
        setUpObserver()
    }

    private fun showMessage(message : String?) {
        _binding.scrollView.visibility = View.GONE
        Handler().postDelayed({
            _binding.loadingBar.mainProgressBarLayout.visibility = View.GONE
            _binding.responseMessage.visibility = View.VISIBLE
            _binding.responseMessage.text = message
        }, 500)
    }

    private fun setUpButtonClick() {
        //Panic Button
        _binding.panicBtn.setOnClickListener {
            showPanicDialog()
        }
        //Map Zoom In Button
        _binding.zoomInBtn.setOnClickListener{
            findNavController().navigate(R.id.action_mapTrackingoFragment_to_zoomedMapFragment)
        }
    }

    //Dialog Box for the first time appearance of Tracking Page
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

    //Dialog box for Panic Button
    private fun showPanicDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Raise Panic Request")
        alertDialogBuilder.setMessage("Are you sure you want to send a panic request?")

        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
            // Handle the positive button click
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            // Handle the negative button click
            dialog.dismiss()
        }
        alertDialogBuilder.create().show()
    }

    // Extension function to apply spans
    private fun SpannableStringBuilder.applySpans(spans: List<Any>, start: Int, end: Int) {
        spans.forEach { span ->
            this.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun textSpan() {
        // Initialize SpannableStringBuilders
         val spannableStringBuilder1 = SpannableStringBuilder(_binding.feedbackText1.text)
         val spannableStringBuilder2 = SpannableStringBuilder(_binding.feedbackText2.text)
         val spannableStringBuilder3 = SpannableStringBuilder(_binding.disclaimerText1.text)

        // Creating spans
        val boldSpan = StyleSpan(Typeface.BOLD)
        val sizeSpan = AbsoluteSizeSpan(28)
        // Apply ClickableSpan to the "feedback" part
         val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(widget: View) {
            // Click will redirect to 1st feedback fragment
                findNavController().navigate(R.id.action_mapTrackingoFragment_to_feedbackFutureExpectationFragment)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.blue) // Change the link color
                ds.isUnderlineText = false
            }
        }

        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Click will redirect to 2nd feedback fragment
                findNavController().navigate(R.id.action_mapTrackingoFragment_to_feedbackFragment)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.blue) // Change the link color
                ds.isUnderlineText = false
            }
        }

        // Apply spans to feedbackText1
        _binding.feedbackText1.text.let {
            val startFeedback1 = it.indexOf("feedback")
            val endFeedback1 = startFeedback1 + "feedback".length

            val startTrackingo = it.indexOf("Trackingo.")
            val endTrackingo = startTrackingo + "Trackingo".length

            spannableStringBuilder1.applySpans(listOf(clickableSpan1), startFeedback1, endFeedback1)
            spannableStringBuilder1.applySpans(listOf(boldSpan), startTrackingo, endTrackingo)
        }

        // Apply spans to feedbackText2
        _binding.feedbackText2.text.let {
            val startHere = it.indexOf("here")
            val endHere = startHere + "here".length

            val startBusOperator = it.indexOf("Bus Operator.")
            val endBusOperator = startBusOperator + "Bus Operator".length

            spannableStringBuilder2.applySpans(listOf(clickableSpan2), startHere, endHere)
            spannableStringBuilder2.applySpans(listOf(boldSpan), startBusOperator, endBusOperator)
        }

        spannableStringBuilder3.applySpans(listOf(boldSpan, UnderlineSpan(), sizeSpan), 0, 10)

        // Assign the spannable text to TextViews
        _binding.feedbackText1.text = spannableStringBuilder1
        _binding.feedbackText2.text = spannableStringBuilder2
        _binding.disclaimerText1.text = spannableStringBuilder3

        // Ensure movement method is set
        _binding.feedbackText1.movementMethod = LinkMovementMethod.getInstance()
        _binding.feedbackText2.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun resizeCustomMarker(drawableRes: Int, width: Int, height: Int) : Bitmap {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, drawableRes), width, height, false)
    }

    private fun setUpObserver() {
        locTimeViewModel.fetchDetailsResponse.observe(requireActivity()) { response ->
            when (response?.status) {
                200 -> {
                    Log.d("tag", "Fetching data of location and time")
                    _binding.scrollView.visibility = View.VISIBLE
                    val adapter = BpDpAdapter(emptyList())
                    _binding.recyclerView.adapter = adapter
                    response.locationTimeDetails?.let { adapter.updateData(it) }
                }
                302, 500 -> {
                    Log.d("tag", "Message received")
                }
                else -> {
                    Log.e("tag", "Error")
                }
            }
        }

        coordinatesViewModel.fetchCoordinatesResponse.observe(requireActivity()) {
            it?.getContentIfNotHandled()?.let {response ->
                when (response?.status) {
                    200 -> {
                        _binding.loadingBar.mainProgressBarLayout.visibility = View.GONE
                        Log.d("tag", "Fetching data of coordinates")
                        _binding.scrollView.visibility = View.VISIBLE

                        response.journeyDetails?.let{
                            _binding.apply{
                                serviceName.text = it.serviceNum
                                location.text = it.srcName
                                copyLocation.text = it.srcAddress
                                busNumber.text = it.vehicleNum
                                busTime.text = it.srcTime
                            }
                        }

                        response.coordinates?.let{ locations ->
                            if(::googleMap.isInitialized) {
                                googleMap.clear()
                                for(latLng in locations) {
                                    latLng.lat_long?.let {
                                        val markerOptions = MarkerOptions()
                                            .position(com.google.android.gms.maps.model.LatLng(it[0], it[1]))
                                            .title(latLng.servicePlace)
                                            .snippet(latLng.stageType)
                                            .icon(BitmapDescriptorFactory.fromBitmap(resizeCustomMarker(R.drawable.pick, 50, 50)))
                                        googleMap.addMarker(markerOptions)
                                    }
                                }
                                if(locations.isNotEmpty()) {
                                    locations[0].lat_long?.let {
                                        val markerOptions = MarkerOptions()
                                            .position(com.google.android.gms.maps.model.LatLng(it[0], it[1]))
                                            .title(locations[0].servicePlace)
                                            .snippet(locations[0].stageType)
                                            .icon(BitmapDescriptorFactory.fromBitmap(resizeCustomMarker(R.drawable.bus, 50, 50)))
                                        googleMap.addMarker(markerOptions)
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(com.google.android.gms.maps.model.LatLng(it[0], it[1]), 12f))
                                    }
                                }
                            }
                        }

                        showDialog()
                        sharedViewModel.setDialogShown(false) // Reset the flag
                    }
                    302, 500 -> {
                        Log.d("tag", "Message received")
                        showMessage(response.message)
                    }
                    else -> {
                        Log.e("tag", "Error")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }

}