package com.bitlasoft.trackingo.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
    private var _binding: ZoomedmapFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapTrackingoBinding: MaptrackingoFragmentBinding
    private val coordinatesViewModel : CoordinatesViewModel by viewModel()
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflating the layout
        _binding = ZoomedmapFragmentBinding.inflate(inflater, container, false)
        setUpObserver()
        mapView = binding.mapViewZoomed
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            googleMap = map
            coordinatesViewModel.getCoordinates("",12, "mobile")
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isCompassEnabled = true
            googleMap.uiSettings.isZoomGesturesEnabled = true
            googleMap.uiSettings.isMapToolbarEnabled = false
            googleMap.uiSettings.isScrollGesturesEnabled = true
        }
        _binding!!.zoomOutBtn.setOnClickListener {
            parentFragmentManager
                .popBackStack()
        }
        return binding.root
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
    private fun resizeCustomMarker(drawableRes: Int, width: Int, height: Int) : Bitmap {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, drawableRes), width, height, false)
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}