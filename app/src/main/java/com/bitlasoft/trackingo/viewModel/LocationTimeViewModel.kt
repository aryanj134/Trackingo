package com.bitlasoft.trackingo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlasoft.trackingo.data.str
import com.bitlasoft.trackingo.domain.pojo.response.LocTime
import com.bitlasoft.trackingo.domain.pojo.response.LocationTimeDetails
import com.bitlasoft.trackingo.domain.repository.LocationTimeRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationTimeViewModel (private val locTimeRepository: LocationTimeRepository): ViewModel() {
    private val _locTimeData : MutableLiveData<List<LocTime>?> = MutableLiveData()
    val locTimeData: LiveData<List<LocTime>?> get() = _locTimeData

    fun getLocationTimeDetails(currentStatus: Boolean, shortKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = locTimeRepository.fetchDetails(currentStatus, shortKey)

//                when(response.body()?.status){
//                    200 -> {}
//                    302 -> {}
//                    else -> {}
//                }
                if (response.isSuccessful||true) {
                    Log.d("LocationTimeViewModel", "Fetching data of location and time")
                    var response = Gson().fromJson<LocationTimeDetails>(str,LocationTimeDetails::class.java)
                    _locTimeData.postValue(response.locationTimeDetails)
//                    _locTimeData.postValue(response.body()?.locationTimeDetails)

                } else {
                    Log.e("tag", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("tag", "Exception: ${e.message}")
            }
        }
    }
}