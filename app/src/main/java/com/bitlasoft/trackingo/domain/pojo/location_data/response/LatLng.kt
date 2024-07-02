package com.bitlasoft.trackingo.domain.pojo.location_data.response

import com.google.gson.annotations.SerializedName

data class LatLng(
    @SerializedName("lat_long")
    val lat_long: List<Double>?,

    @SerializedName("stage_type")
    val stageType: String?,

    @SerializedName("sp_name")
    val servicePlace: String?
)

data class ServiceDetails(
    @SerializedName("service_number")
    val serviceNum : String?,

    @SerializedName("vehicle_number")
    val vehicleNum : String?,

    @SerializedName("boarding_address")
    val srcAddress : String?,

    @SerializedName("boarding_time")
    val srcTime : String?,

    @SerializedName("boarding_at")
    val srcName : String?
)

data class Coordinates(
    @SerializedName("journey_details")
    val journeyDetails : ServiceDetails?,

    @SerializedName("all_service_places")
    val coordinates : List<LatLng>?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("message")
    val message: String?
)