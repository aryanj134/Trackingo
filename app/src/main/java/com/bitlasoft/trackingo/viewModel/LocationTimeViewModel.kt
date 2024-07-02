package com.bitlasoft.trackingo.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlasoft.trackingo.databinding.MaptrackingoFragmentBinding
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LocTime
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LocationTimeDetails
import com.bitlasoft.trackingo.domain.repository.LocationTimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationTimeViewModel (private val locTimeRepository: LocationTimeRepository): ViewModel() {

    private val _fetchDetailsResponse : MutableLiveData<LocationTimeDetails?> = MutableLiveData()
    val fetchDetailsResponse : LiveData<LocationTimeDetails?> get() = _fetchDetailsResponse

    fun getLocationTimeDetails(currentStatus: Boolean, shortKey: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = locTimeRepository.fetchDetails(currentStatus, shortKey)
                _fetchDetailsResponse.postValue(response.body())
            } catch (e: Exception) {
                Log.e("tag", "Exception: ${e.message}")
            }
        }
    }
}