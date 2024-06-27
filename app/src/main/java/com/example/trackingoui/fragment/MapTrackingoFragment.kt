package com.example.trackingoui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackingoui.domain.pojo.response.LocTime
import com.example.trackingoui.R
import com.example.trackingoui.adapter.BpDpAdapter
import com.example.trackingoui.databinding.ItemLayoutBinding
import com.example.trackingoui.databinding.MaptrackingoFragmentBinding
import com.example.trackingoui.domain.pojo.response.LatLng
import com.example.trackingoui.viewModel.CoordinatesViewModel
import com.example.trackingoui.viewModel.LocationTimeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapTrackingoFragment: Fragment() {
    private lateinit var itemLayoutBinding: ItemLayoutBinding
    private var _binding: MaptrackingoFragmentBinding? = null
    private val binding get() = _binding!!
    private val locTimeViewModel: LocationTimeViewModel by viewModel()
    private val coordinatesViewModel : CoordinatesViewModel by viewModel()
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflating the layout
        _binding = MaptrackingoFragmentBinding.inflate(inflater, container, false)

        //Setting the recyclerView layout as Horizontal
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        _binding!!.recyclerView.layoutManager = horizontalLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = _binding!!.mapLayoutView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            googleMap = map
            coordinatesViewModel.getCoordinates("",12,"mobile")
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isCompassEnabled = true
            googleMap.uiSettings.isZoomGesturesEnabled = true
            googleMap.uiSettings.isMapToolbarEnabled = false
            googleMap.uiSettings.isScrollGesturesEnabled = true
        }
        // Fetch Data
        locTimeViewModel.getLocationTimeDetails(true, "")
        val adapter = BpDpAdapter(emptyList())
        _binding!!.recyclerView.adapter = adapter

        //observing the live data
        locTimeViewModel.locTimeData.observe(viewLifecycleOwner, Observer { itemList ->
            itemList?.let{ adapter.updateData(it) }
        })
        textSpan()
        setUpObserver()

        _binding!!.panicBtn.setOnClickListener {
            showPanicDialog()
        }
        _binding!!.zoomInBtn.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_constraintlayout, ZoomedMapFragment())
                .addToBackStack(null)
                .commit()
        }
    }

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
         val spannableStringBuilder1 = SpannableStringBuilder(binding.feedbackText1.text)
         val spannableStringBuilder2 = SpannableStringBuilder(binding.feedbackText2.text)
         val spannableStringBuilder3 = SpannableStringBuilder(binding.disclaimerText1.text)

        // Create spans
        val boldSpan = StyleSpan(Typeface.BOLD)
        val sizeSpan = AbsoluteSizeSpan(28)
        // Apply ClickableSpan to the "feedback" part
         val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
            // Handle the click event to navigate to another fragment

            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.blue) // Change the link color
            }
        }
        // Apply spans to feedbackText1
        binding.feedbackText1.text.let {
            val startFeedback1 = it.indexOf("feedback")
            val endFeedback1 = startFeedback1 + "feedback".length

            val startTrackingo = it.indexOf("Trackingo.")
            val endTrackingo = startTrackingo + "Trackingo".length

            spannableStringBuilder1.applySpans(listOf(clickableSpan), startFeedback1, endFeedback1)
            spannableStringBuilder1.applySpans(listOf(boldSpan), startTrackingo, endTrackingo)
        }

        // Apply spans to feedbackText2
        binding.feedbackText2.text.let {
            val startHere = it.indexOf("here")
            val endHere = startHere + "here".length

            val startBusOperator = it.indexOf("Bus Operator.")
            val endBusOperator = startBusOperator + "Bus Operator".length

            spannableStringBuilder2.applySpans(listOf(clickableSpan), startHere, endHere)
            spannableStringBuilder2.applySpans(listOf(boldSpan), startBusOperator, endBusOperator)
        }

        // Apply spans to disclaimerText1
        spannableStringBuilder3.applySpans(listOf(boldSpan, UnderlineSpan(), sizeSpan), 0, 10)

        // Assign the spannable text to TextViews
        binding.feedbackText1.text = spannableStringBuilder1
        binding.feedbackText2.text = spannableStringBuilder2
        binding.disclaimerText1.text = spannableStringBuilder3
    }

    private fun resizeCustomMarker(drawableRes: Int, width: Int, height: Int) : Bitmap {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, drawableRes), width, height, false)
    }

    private fun setUpObserver() {
        coordinatesViewModel.locationData.observe(viewLifecycleOwner, Observer { locations ->
            if(locations != null && ::googleMap.isInitialized) {
                googleMap.clear()
                for(latLng in locations) {
                    latLng.lat_long?.let {
                        val markerOptions = MarkerOptions()
                            .position(com.google.android.gms.maps.model.LatLng(it[0], it[1]))
                            .title(latLng.servicePlace)
                            .snippet(latLng.stageType)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeCustomMarker(R.drawable.bus, 50, 50)))
                        googleMap.addMarker(markerOptions)
                    }
                }
                if(locations.isNotEmpty()) {
                    locations[0].lat_long?.let {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(com.google.android.gms.maps.model.LatLng(it[0], it[1]), 12f))
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }
}