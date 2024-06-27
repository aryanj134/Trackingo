package com.example.trackingoui.domain.pojo.response

import com.google.gson.annotations.SerializedName

data class LatLng(
    @SerializedName("lat_long")
    val lat_long: List<Double>?,

    @SerializedName("stage_type")
    val stageType: String?,

    @SerializedName("sp_name")
    val servicePlace: String?
)

data class Coordinates(
    @SerializedName("all_service_places")
    val coordinates : List<LatLng>?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("message")
    val message: String?
)