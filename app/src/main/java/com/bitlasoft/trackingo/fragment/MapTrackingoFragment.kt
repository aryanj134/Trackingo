package com.bitlasoft.trackingo.fragment

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.activity.MainActivity
import com.bitlasoft.trackingo.adapter.BpDpAdapter
import com.bitlasoft.trackingo.databinding.ItemLayoutBinding
import com.bitlasoft.trackingo.databinding.MaptrackingoFragmentBinding
import com.bitlasoft.trackingo.databinding.TrankingoDialogPnrBinding
import com.bitlasoft.trackingo.utils.isInternetAvailable
import com.bitlasoft.trackingo.viewModel.CoordinatesViewModel
import com.bitlasoft.trackingo.viewModel.LocationTimeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapTrackingoFragment : Fragment() {


    private val _binding by lazy { MaptrackingoFragmentBinding.inflate(layoutInflater) }

    //    private val binding get() = _binding!!
    private lateinit var itemLayoutBinding: ItemLayoutBinding
    private val locTimeViewModel: LocationTimeViewModel by viewModel()
    private val coordinatesViewModel: CoordinatesViewModel by viewModel()
    private var googleMap: GoogleMap? = null
    private var mapView: MapView? = null
    private var isDialogShown = true
    var isPnr = false
    var shortKey = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Setting the recyclerView layout as Horizontal
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        _binding.recyclerView.layoutManager = horizontalLayoutManager

        observer()

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null && arguments?.containsKey("type")==true ){
            isPnr = arguments?.getString("type").equals("pnr",false)
            if (isPnr){
                // later implementation
            } else{
                shortKey = arguments?.getString("shortKey")?:""
            }
        }
        mapView = _binding.mapLayoutView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            googleMap = map
            locTimeViewModel.getLocationTimeDetails(true, shortKey)
            coordinatesViewModel.getCoordinates(shortKey, 12, "mobile")
            googleMap?.uiSettings?.apply {
                isZoomControlsEnabled = true
                isCompassEnabled = true
                isMapToolbarEnabled = true
                isZoomGesturesEnabled = true
                isScrollGesturesEnabled = true
            }
        }

        // Back Button to ShortKeyFragment
        _binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        setUpButtonClick()
        textSpan()
        setUpObserver()
    }

    private fun showMessage(message: String?) {
        _binding.scrollView.visibility = View.GONE
        Handler().postDelayed({
            _binding.loadingBar.mainProgressBarLayout.visibility = View.GONE
            _binding.responseMessage.visibility = View.VISIBLE
            _binding.responseMessage.text = message
        }, 400)
    }

    private fun observer() {
        lifecycleScope.launch {
            if (!isInternetAvailable(requireContext())){
                _binding.loadingBar.mainProgressBarLayout.visibility = View.GONE

                showMessage("Something is Technically Wrong, We are going to fix it up and things will be normal soon.")
            }
            MainActivity.isInternet.collect {
                if (it) {
                    _binding.responseMessage.visibility = View.GONE
                    locTimeViewModel.getLocationTimeDetails(true, shortKey)

                } else {
                    showMessage("Something is Technically Wrong, We are going to fix it up and things will be normal soon.")
                }
            }
        }
    }

    private fun setUpButtonClick() {
        //Panic Button
        _binding.panicBtn.setOnClickListener {
            showPanicDialog()
        }
        //Map Zoom In Button
        _binding.zoomInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mapTrackingoFragment_to_zoomedMapFragment, Bundle().apply {
                putString("shortKey", shortKey)
            })
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
        val spannableStringBuilder1 = SpannableStringBuilder(_binding.feedbackText.text)
        val spannableStringBuilder2 = SpannableStringBuilder(_binding.disclaimerText.text)

        // Creating spans
        val boldSpan = StyleSpan(Typeface.BOLD)
        val sizeSpan = AbsoluteSizeSpan(28)
        // Apply ClickableSpan to the "feedback" part
        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Click will redirect to 1st feedback fragment
                findNavController().navigate(R.id.action_mapTrackingoFragment_to_feedbackFutureExpectationFragment, Bundle().apply {
                    putString("shortKey", shortKey)
                })
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
                findNavController().navigate(R.id.action_mapTrackingoFragment_to_feedbackFragment, Bundle().apply {
                    putString("shortKey", shortKey)
                })
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context!!, R.color.blue) // Change the link color
                ds.isUnderlineText = false
            }
        }

        // Apply spans to feedbackText1
        _binding.feedbackText.text.let {
            val startFeedback = it.indexOf("feedback")
            val endFeedback = startFeedback + "feedback".length

            val startTrackingo = it.indexOf("Trackingo.")
            val endTrackingo = startTrackingo + "Trackingo".length

            val startHere = it.indexOf("here")
            val endHere = startHere + "here".length

            val startBusOperator = it.indexOf("Bus Operator.")
            val endBusOperator = startBusOperator + "Bus Operator.".length

            spannableStringBuilder1.applySpans(listOf(clickableSpan1), startFeedback, endFeedback)
            spannableStringBuilder1.applySpans(listOf(clickableSpan2), startHere, endHere)
            spannableStringBuilder1.applySpans(listOf(boldSpan), startBusOperator, endBusOperator)
            spannableStringBuilder1.applySpans(listOf(boldSpan), startTrackingo, endTrackingo)
        }

        _binding.disclaimerText.text.let {
            spannableStringBuilder2.applySpans(listOf(boldSpan, UnderlineSpan(), sizeSpan), 0, 10)
        }

        // Assign the spannable text to TextViews
        _binding.feedbackText.text = spannableStringBuilder1
        _binding.disclaimerText.text = spannableStringBuilder2

        // Ensure movement method is set
        _binding.feedbackText.movementMethod = LinkMovementMethod.getInstance()
        _binding.disclaimerText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun resizeCustomMarker(drawableRes: Int, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, drawableRes),
            width,
            height,
            false
        )
    }

    private fun setUpObserver() {

        val arrivalTime: MutableList<String?> = mutableListOf()
        val deptTime: MutableList<String?> = mutableListOf()
        val scheduledTime: MutableList<String?> = mutableListOf()
        var speed: Int = 0
        var dateTime: String? = null

        locTimeViewModel.fetchDetailsResponse.observe(requireActivity()) { response ->
            when (response?.status) {
                200 -> {
                    _binding.loadingBar.mainProgressBarLayout.visibility = View.GONE
                    Log.d("tag", "Fetching data of location and time")
                    _binding.scrollView.visibility = View.VISIBLE
                    val adapter = BpDpAdapter(emptyList())
                    _binding.recyclerView.adapter = adapter
                    response.locationTimeDetails?.let { adapter.updateData(it) }
                    for (i in response.locationTimeDetails?.indices!!) {
                        arrivalTime.add(response.locationTimeDetails[i].arrivalTime)
                        deptTime.add(response.locationTimeDetails[i].deptTime)
                        scheduledTime.add(response.locationTimeDetails[i].scheduledTime)
                    }

                    response.currentCoordinates?.currentLoc?.let {
                        val markerOptions = MarkerOptions()
                            .position(
                                com.google.android.gms.maps.model.LatLng(
                                    it[0]!!,
                                    it[1]!!
                                )
                            )
                            .title("speed: $speed\n km/hr \n Time: $dateTime")
                            .snippet("")
                            .icon(
                                BitmapDescriptorFactory.fromBitmap(
                                    resizeCustomMarker(
                                        R.drawable.bus,
                                        80,
                                        100
                                    )
                                )
                            )
                        googleMap?.addMarker(markerOptions)
                        googleMap?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                com.google.android.gms.maps.model.LatLng(
                                    it[0]!!,
                                    it[1]!!
                                ), 14f
                            )
                        )
                    }

                    _binding.locationBtn.setOnClickListener {
                        response.currentCoordinates?.currentLoc?.let {
                            val markerOptions = MarkerOptions()
                                .position(
                                    com.google.android.gms.maps.model.LatLng(
                                        it[0]!!,
                                        it[1]!!
                                    )
                                )
                                .title("speed: $speed\n km/hr \n Time: $dateTime")
                                .snippet("")
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        resizeCustomMarker(
                                            R.drawable.bus,
                                            80,
                                            100
                                        )
                                    )
                                )
                            googleMap?.addMarker(markerOptions)
                            googleMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    com.google.android.gms.maps.model.LatLng(
                                        it[0]!!,
                                        it[1]!!
                                    ), 14f
                                )
                            )
                        }
                    }

                    for (i in response.locationTimeDetails.indices) {
                        if (response.curr_sp_id == response.locationTimeDetails[i].id) {
                            if (adapter.itemCount > 0) {
                                val itemView =
                                    _binding.recyclerView.findViewHolderForAdapterPosition(i)?.itemView
                                if (itemView != null) {
                                    itemLayoutBinding = ItemLayoutBinding.bind(itemView)
                                    itemLayoutBinding.busTracker.visibility = View.VISIBLE
                                    if (response.is_passed == false)
                                        itemLayoutBinding.busTracker.translationX =
                                            itemLayoutBinding.trackingDividerMain.width * 0.15f
                                    else
                                        itemLayoutBinding.circleView.translationX =
                                            itemLayoutBinding.circleView.width * 0.5f
                                }
                            }
                        } else {
                            if (adapter.itemCount > 0) {
                                val itemView =
                                    _binding.recyclerView.findViewHolderForAdapterPosition(i)?.itemView
                                if (itemView != null) {
                                    itemLayoutBinding = ItemLayoutBinding.bind(itemView)
                                    itemLayoutBinding.busTracker.visibility = View.INVISIBLE
                                }
                            }
                        }

                        if (response.locationTimeDetails[i].skippedStatus == true) {
                            if (adapter.itemCount > 0) {
                                val itemView =
                                    _binding.recyclerView.findViewHolderForAdapterPosition(0)?.itemView
                                if (itemView != null) {
                                    itemLayoutBinding = ItemLayoutBinding.bind(itemView)
                                    itemLayoutBinding.busTracker.visibility = View.VISIBLE
                                    itemLayoutBinding.busTracker.translationX =
                                        itemLayoutBinding.trackingDividerMain.width * 0.5f
                                    itemLayoutBinding.circleView.background =
                                        ContextCompat.getDrawable(requireContext(), R.color.red)
                                }
                            }
                        }
                    }
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
            it?.getContentIfNotHandled()?.let { response ->
                when (response.status) {
                    200 -> {
                        _binding.loadingBar.mainProgressBarLayout.visibility = View.GONE
                        Log.d("tag", "Fetching data of coordinates")
                        _binding.scrollView.visibility = View.VISIBLE

                        if (response.bustracking?.isNotEmpty() == true) {
                            speed = response.bustracking[0]?.details?.speed!!
                            dateTime = response.bustracking[0]?.details?.dateTime!!
                        }

                        response.journeyDetails?.let {
                            _binding.apply {
                                serviceName.text = it.serviceNum
                                location.text = it.srcName
                                copyLocation.text = it.srcAddress
                                busNumber.text = it.vehicleNum
                                busTime.text = it.srcTime
                            }
                        }

                        response.coordinates?.let { locations ->
                            var i = 0
                            for (latLng in locations) {
                                if (latLng.stageType == "boarding") {
                                    latLng.lat_long?.let {
                                        val markerOptions = MarkerOptions()
                                            .position(
                                                com.google.android.gms.maps.model.LatLng(
                                                    it[0],
                                                    it[1]
                                                )
                                            )
                                            .title(latLng.servicePlace)
//                                            .snippet("Scheduled Time:\n${scheduledTime[i]}")
                                            .icon(
                                                BitmapDescriptorFactory.fromBitmap(
                                                    resizeCustomMarker(R.drawable.pick, 60, 60)
                                                )
                                            )
                                        googleMap?.addMarker(markerOptions)
                                        i++
                                    }
                                } else if (latLng.stageType == "dropoff") {
                                    latLng.lat_long?.let {
                                        val markerOptions = MarkerOptions()
                                            .position(
                                                com.google.android.gms.maps.model.LatLng(
                                                    it[0],
                                                    it[1]
                                                )
                                            )
                                            .title(latLng.servicePlace)
//                                            .snippet("Scheduled Time:\n${scheduledTime[i]}")
                                            .icon(
                                                BitmapDescriptorFactory.fromBitmap(
                                                    resizeCustomMarker(R.drawable.drop, 60, 60)
                                                )
                                            )
                                        googleMap?.addMarker(markerOptions)
                                        i++
                                    }
                                }
                            }
                        }
                        if (isDialogShown)
                            showDialog()
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
        isDialogShown = false
        mapView?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }

}