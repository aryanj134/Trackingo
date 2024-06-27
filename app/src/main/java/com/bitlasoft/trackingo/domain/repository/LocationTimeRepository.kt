package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.APIInterface
import com.bitlasoft.trackingo.domain.pojo.response.LocationTimeDetails
import retrofit2.Response

class LocationTimeRepository(private var locTimeDetails: APIInterface) {
    suspend fun fetchDetails(currentStatus: Boolean, shortKey: String) : Response<LocationTimeDetails> {
        return locTimeDetails.fetchDetails(currentStatus, shortKey)
    }
}