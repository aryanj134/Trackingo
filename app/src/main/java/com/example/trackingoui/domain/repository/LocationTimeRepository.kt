package com.example.trackingoui.domain.repository

import com.example.trackingoui.data.APIInterface
import com.example.trackingoui.domain.pojo.response.LocationTimeDetails
import retrofit2.Response

class LocationTimeRepository(private var locTimeDetails: APIInterface) {
    suspend fun fetchDetails(currentStatus: Boolean, shortKey: String) : Response<LocationTimeDetails> {
        return locTimeDetails.fetchDetails(currentStatus, shortKey)
    }
}