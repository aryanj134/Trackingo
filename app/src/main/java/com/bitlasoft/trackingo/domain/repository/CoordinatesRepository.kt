package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.domain.pojo.location_data.response.Coordinates
import retrofit2.Response

class CoordinatesRepository(private var coordinates : ApiInterface) {
    suspend fun fetchCoordinates(shortKey: String?, zoomPosition: Int?, platform: String?) : Response<Coordinates> {
        return coordinates.fetchCoordinates(shortKey, zoomPosition, platform)
    }
}
