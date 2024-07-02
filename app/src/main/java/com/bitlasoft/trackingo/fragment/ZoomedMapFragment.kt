package com.bitlasoft.trackingo.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.MaptrackingoFragmentBinding
import com.bitlasoft.trackingo.databinding.ZoomedmapFragmentBinding
import com.bitlasoft.trackingo.viewModel.CoordinatesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class ZoomedMapFragment: Fragment() {
    private lateinit var _binding: ZoomedmapFragmentBinding
//    private var _mapTrackingoBinding: MaptrackingoFragmentBinding? = null
//    private val maptrackingoBinding get() = _mapTrackingoBinding!!
    private val coordinatesViewModel : CoordinatesViewModel by viewModel()
    private lateinit var googleMap: GoogleMap
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
            coordinatesViewModel.getCoordinates("",12, "mobile")
            googleMap.uiSettings.apply {
                isZoomControlsEnabled = true
                isCompassEnabled = true
                isZoomGesturesEnabled = true
                isMapToolbarEnabled = false
                isScrollGesturesEnabled = true
            }
        }
        _binding.zoomOutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_zoomedMapFragment_to_mapTrackingoFragment)
        }
        return _binding.root
    }

    private fun setUpObserver() {
        coordinatesViewModel.fetchCoordinatesResponse.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { response ->
            response.coordinates?.let { locations ->
                if (::googleMap.isInitialized) {
                    googleMap.clear()
                    for (latLng in locations) {
                        latLng.lat_long?.let {
                            val markerOptions = MarkerOptions().position(
                                com.google.android.gms.maps.model.LatLng(it[0], it[1])
                            )
                                .title(latLng.servicePlace).snippet(latLng.stageType)
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        resizeCustomMarker(R.drawable.pick, 50, 50)
                                    )
                                )
                            googleMap.addMarker(markerOptions)
                        }
                    }
                    if (locations.isNotEmpty()) {
                        locations[0].lat_long?.let {
                            val markerOptions = MarkerOptions()
                                .position(com.google.android.gms.maps.model.LatLng(it[0], it[1]))
                                .title(locations[0].servicePlace)
                                .snippet(locations[0].stageType)
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        resizeCustomMarker(R.drawable.bus, 50, 50)
                                    )
                                )
                            googleMap.addMarker(markerOptions)
                            googleMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    com.google.android.gms.maps.model.LatLng(
                                        it[0],
                                        it[1]
                                    ), 12f
                                )
                            )
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