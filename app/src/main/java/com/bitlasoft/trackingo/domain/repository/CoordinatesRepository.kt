package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.APIInterface
import com.bitlasoft.trackingo.domain.pojo.response.Coordinates
import retrofit2.Response

class CoordinatesRepository(private var coordinates : APIInterface) {
    suspend fun fetchCoordinates(shortKey: String, zoomPosition: Int, platform: String) : Response<Coordinates> {
        return coordinates.fetchCoordinates(shortKey, zoomPosition, platform)
    }
}
