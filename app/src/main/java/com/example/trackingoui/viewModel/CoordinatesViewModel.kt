package com.example.trackingoui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackingoui.domain.pojo.response.Coordinates
import com.example.trackingoui.domain.pojo.response.LatLng
import com.example.trackingoui.domain.repository.CoordinatesRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoordinatesViewModel (private val coordinatesRepository: CoordinatesRepository): ViewModel() {
    private val _locationData : MutableLiveData<List<LatLng>?> = MutableLiveData()
    val locationData: MutableLiveData<List<LatLng>?> get() = _locationData

    var str2 = "{\n" +
            "   \"status\": 200,\n" +
            "   \"journey_details\": {\n" +
            "       \"service_number\": \"AP-406A\",\n" +
            "       \"service\": \"Visakhapatnam-Kadapa\",\n" +
            "       \"vehicle_number\": \"AP-07-TH-8889\",\n" +
            "       \"boarding_address\": \"Seethampeta\",\n" +
            "       \"boarding_time\": \"03:20 PM\",\n" +
            "       \"travel_date\": \"2024-06-21\",\n" +
            "       \"driver_name\": \"Suresh M \",\n" +
            "       \"phone_number\": \" 8074569224 \",\n" +
            "       \"boarding_at\": \"Seethampeta\",\n" +
            "       \"lat_long\": [\n" +
            "           \"17.731687\",\n" +
            "           \"83.306863\"\n" +
            "       ],\n" +
            "       \"dropoff\": \"Pamuru\",\n" +
            "       \"dropoff_lat_long\": [\n" +
            "           \"15.096821\",\n" +
            "           \"79.41094\"\n" +
            "       ],\n" +
            "       \"operator_logo\": null,\n" +
            "       \"operator_name\": \"New Morning Star Travels\",\n" +
            "       \"android_app_url\": \"\",\n" +
            "       \"ios_app_url\": \"\",\n" +
            "       \"subdomain\": \"mstbus\",\n" +
            "       \"pnr_number\": \"AB2070306\",\n" +
            "       \"short_code\": \"HP64X5\"\n" +
            "   },\n" +
            "   \"other_details\": {\n" +
            "       \"service_place_name\": \"Seethampeta\",\n" +
            "       \"service_place_stage_type\": \"boarding\"\n" +
            "   },\n" +
            "   \"track_customer\": [\n" +
            "       {\n" +
            "           \"origin\": [\n" +
            "               16.316882777777778,\n" +
            "               80.48155\n" +
            "           ],\n" +
            "           \"destination\": [\n" +
            "               16.316882777777778,\n" +
            "               80.48155\n" +
            "           ],\n" +
            "           \"details\": {\n" +
            "               \"speed\": 0.0,\n" +
            "               \"timestamp\": \"May 30 11:09:05\",\n" +
            "               \"location\": \"Takkellapadu, Pedakakani\",\n" +
            "               \"astl_id\": 1,\n" +
            "               \"class_name\": \"AssetTrackerLatest\"\n" +
            "           }\n" +
            "       }\n" +
            "   ],\n" +
            "   \"all_service_places\": [\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.824516,\n" +
            "               83.35227\n" +
            "           ],\n" +
            "           \"sp_id\": 8627979,\n" +
            "           \"sp_name\": \"Kommadi village\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 0\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.823437,\n" +
            "               83.356632\n" +
            "           ],\n" +
            "           \"sp_id\": 8627980,\n" +
            "           \"sp_name\": \"KOMMADI\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 1\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.802035,\n" +
            "               83.353047\n" +
            "           ],\n" +
            "           \"sp_id\": 8627981,\n" +
            "           \"sp_name\": \"Madhurawada\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 1\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.783288,\n" +
            "               83.375432\n" +
            "           ],\n" +
            "           \"sp_id\": 8627982,\n" +
            "           \"sp_name\": \"Gitam University\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 0\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.782593,\n" +
            "               83.35776\n" +
            "           ],\n" +
            "           \"sp_id\": 8627983,\n" +
            "           \"sp_name\": \"yendada\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 0\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.74826,\n" +
            "               83.331618\n" +
            "           ],\n" +
            "           \"sp_id\": 8627984,\n" +
            "           \"sp_name\": \"Hanumanthavaka\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 0\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.742861,\n" +
            "               83.327989\n" +
            "           ],\n" +
            "           \"sp_id\": 7541975,\n" +
            "           \"sp_name\": \"Mvp Colony Sector 2\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 0\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.728263,\n" +
            "               83.312105\n" +
            "           ],\n" +
            "           \"sp_id\": 7541976,\n" +
            "           \"sp_name\": \"Rama Talkies\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 0\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.724644,\n" +
            "               83.307389\n" +
            "           ],\n" +
            "           \"sp_id\": 7541977,\n" +
            "           \"sp_name\": \"RTC Complex\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 3\n" +
            "       },\n" +
            "       {\n" +
            "           \"stage_type\": \"boarding\",\n" +
            "           \"lat_long\": [\n" +
            "               17.731687,\n" +
            "               83.306863\n" +
            "           ],\n" +
            "           \"sp_id\": 7541978,\n" +
            "           \"sp_name\": \"Seethampeta\",\n" +
            "           \"radius\": \"300.0\",\n" +
            "           \"passengers_count\": 1\n" +
            "       },\n"

    fun getCoordinates(shortKey: String, zoomPosition: Int, platform: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = coordinatesRepository.fetchCoordinates(shortKey, zoomPosition, platform)
                if (response.isSuccessful||true) {
                    var response = Gson().fromJson<Coordinates>(str2, Coordinates::class.java)
                    Log.d("tag", "Fetching data of coordinates")
                    _locationData.postValue(response.coordinates)
                } else {
                    Log.e("tag", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("tag", "Exception: ${e.message}")
            }
        }
    }


}