package com.bitlasoft.trackingo.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ZoomedmapFragmentBinding
import com.bitlasoft.trackingo.utils.Constants
import com.bitlasoft.trackingo.viewModel.CoordinatesViewModel
import com.bitlasoft.trackingo.viewModel.LocationTimeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class ZoomedMapFragment: Fragment() {
    private lateinit var _binding: ZoomedmapFragmentBinding
    private val coordinatesViewModel : CoordinatesViewModel by viewModel()
    private val locTimeViewModel: LocationTimeViewModel by viewModel()
    private var googleMap: GoogleMap? = null
    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflating the layout
        _binding = ZoomedmapFragmentBinding.inflate(inflater, container, false)
        setUpObserver()
        mapView = _binding.mapViewZoomed
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            googleMap = map
            coordinatesViewModel.getCoordinates(Constants.SHORT_KEY,12, "mobile")
            googleMap?.uiSettings?.apply {
                isZoomControlsEnabled = true
                isMyLocationButtonEnabled = true
                isCompassEnabled = true
                isMapToolbarEnabled = true
                isZoomGesturesEnabled = true
                isScrollGesturesEnabled = true
            }
        }
        _binding.zoomOutBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return _binding.root
    }

    private fun setUpObserver() {
        val arrivalTime: MutableList<String?> = mutableListOf()
        val deptTime: MutableList<String?> = mutableListOf()
        val scheduledTime: MutableList<String?> = mutableListOf()

        locTimeViewModel.fetchDetailsResponse.observe(requireActivity()) { response ->
            when (response?.status) {
                200 -> {
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
                                .title("")
                                .snippet("")
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        resizeCustomMarker(
                                            R.drawable.bus,
                                            60,
                                            80
                                        )
                                    )
                                )
                            googleMap?.addMarker(markerOptions)
                            googleMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    com.google.android.gms.maps.model.LatLng(
                                        it[0]!!,
                                        it[1]!!
                                    ), 18f
                                )
                            )
                    }

                    coordinatesViewModel.fetchCoordinatesResponse.observe(requireActivity()) {
                        it?.getContentIfNotHandled()?.let { response ->
                            if (response.status == 200) {
                                response.coordinates?.let { locations ->
                                        var i = 0
                                        for (latLng in locations) {
                                            if(latLng.stageType == "boarding") {
                                                latLng.lat_long?.let {
                                                    val markerOptions = MarkerOptions()
                                                        .position(
                                                            com.google.android.gms.maps.model.LatLng(
                                                                it[0],
                                                                it[1]
                                                            )
                                                        )
                                                        .title(latLng.servicePlace)
                                                        .snippet("Scheduled Time:\n${scheduledTime[i]}")
                                                        .icon(
                                                            BitmapDescriptorFactory.fromBitmap(
                                                                resizeCustomMarker(R.drawable.pick, 60, 60)
                                                            )
                                                        )
                                                    googleMap?.addMarker(markerOptions)
                                                    i++
                                                }
                                            }
                                            else if(latLng.stageType == "dropoff"){
                                                latLng.lat_long?.let {
                                                    val markerOptions = MarkerOptions()
                                                        .position(
                                                            com.google.android.gms.maps.model.LatLng(
                                                                it[0],
                                                                it[1]
                                                            )
                                                        )
                                                        .title(latLng.servicePlace)
                                                        .snippet("Scheduled Time:\n${scheduledTime[i]}")
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
                            }
                        }
                    }
                }
            }
        }
    }

    private fun resizeCustomMarker(drawableRes: Int, width: Int, height: Int) : Bitmap {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, drawableRes), width, height, false)
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