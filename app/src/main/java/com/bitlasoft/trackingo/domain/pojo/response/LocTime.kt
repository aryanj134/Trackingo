package com.bitlasoft.trackingo.domain.pojo.response

import com.google.gson.annotations.SerializedName

data class LocationTimeDetails(
    @SerializedName("eta_map_data")
    val locationTimeDetails : List<LocTime>?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("message")
    val message: String?
)

data class LocTime (

    @SerializedName("service_place_name")
    val title : String?,

    @SerializedName("running_status")
    val subtitle: String?,

    @SerializedName("skipped")
    val skippedStatus: Boolean?,

    @SerializedName("color")
    val iconState: String?,

    @SerializedName("scheduled_time")
    val scheduledTime: String?,

    @SerializedName("arrival_time")
    val arrivalTime: String?,

    @SerializedName("departure_time")
    val deptTime: String?
)