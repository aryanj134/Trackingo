package com.example.trackingoui.domain.repository

import com.example.trackingoui.data.APIInterface
import com.example.trackingoui.domain.pojo.response.Coordinates
import retrofit2.Response

class CoordinatesRepository(private var coordinates : APIInterface) {
    suspend fun fetchCoordinates(shortKey: String, zoomPosition: Int, platform: String) : Response<Coordinates> {
        return coordinates.fetchCoordinates(shortKey, zoomPosition, platform)
    }
}
