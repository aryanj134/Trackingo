package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.domain.pojo.location_data.response.LocationTimeDetails
import retrofit2.Response

class LocationTimeRepository(private var locTimeDetails: ApiInterface) {
    suspend fun fetchDetails(currentStatus: Boolean, shortKey: String?) : Response<LocationTimeDetails> {
        return locTimeDetails.fetchDetails(currentStatus, shortKey)
    }
}