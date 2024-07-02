package com.bitlasoft.trackingo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlasoft.trackingo.domain.pojo.location_data.response.Coordinates
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LatLng
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LocationTimeDetails
import com.bitlasoft.trackingo.domain.pojo.location_data.response.ServiceDetails
import com.bitlasoft.trackingo.domain.repository.CoordinatesRepository
import com.bitlasoft.trackingo.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoordinatesViewModel (private val coordinatesRepository: CoordinatesRepository): ViewModel() {

    private val _fetchCoordinatesResponse : MutableLiveData<Event<Coordinates?>> = MutableLiveData()
    val fetchCoordinatesResponse : LiveData<Event<Coordinates?>> get() = _fetchCoordinatesResponse

    fun getCoordinates(shortKey: String?, zoomPosition: Int?, platform: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = coordinatesRepository.fetchCoordinates(shortKey, zoomPosition, platform)
                _fetchCoordinatesResponse.postValue(Event(response.body()))
            }
            catch (e: Exception) {
                Log.e("tag", "Exception: ${e.message}")
            }
        }
    }
}