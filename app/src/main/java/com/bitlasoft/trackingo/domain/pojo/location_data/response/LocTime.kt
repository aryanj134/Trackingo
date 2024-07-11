package com.bitlasoft.trackingo.domain.pojo.location_data.response

import com.google.gson.annotations.SerializedName

data class LocationTimeDetails(
    @SerializedName("eta_map_data")
    val locationTimeDetails : List<LocTime>?,

    @SerializedName("current_status_details")
    val currentCoordinates: ServiceLocation?,

    @SerializedName("current_sp_id")
    val curr_sp_id: Int?,

    @SerializedName("is_passed")
    val is_passed: Boolean?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("message")
    val message: String?
)

data class ServiceLocation(
    @SerializedName("lat_long")
    val currentLoc : List<Double?>
)


data class LocTime (

    @SerializedName("service_place_name")
    val title : String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("skipped")
    val skippedStatus: Boolean?,

    @SerializedName("running_status")
    val runningStatus: String?,

    @SerializedName("color")
    val iconState: String?,

    @SerializedName("scheduled_time")
    val scheduledTime: String?,

    @SerializedName("arrival_time")
    val arrivalTime: String?,

    @SerializedName("departure_time")
    val deptTime: String?,

    @SerializedName("delay_time")
    val delayTime: Int?,

    var isBusIconVisible: Boolean? = false,
    var isCross:Boolean=false
)