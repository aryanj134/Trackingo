package com.example.trackingoui.data

import com.example.trackingoui.domain.pojo.response.Coordinates
import com.example.trackingoui.domain.pojo.response.LocationTimeDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("eta_map")
    suspend fun fetchDetails(
        @Query("current_status") currentStatus : Boolean?,
        @Query("key") short_key : String?
    ) : Response<LocationTimeDetails>

    @GET("journey_details")
    suspend fun fetchCoordinates(
        @Query("key") short_key : String?,
        @Query("zoom_position") position : Int?,
        @Query("platform") platform : String?
    ) : Response<Coordinates>

    @GET("panic_details")
    suspend fun panicResponse(
        @Query("short_code") short_key: String?,
        @Query("web_app") web_app: Boolean?
    )
}

var str = "{\n" +
        "   \"status\": 200,\n" +
        "   \"eta_map_data\": [\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542054,\n" +
        "           \"scheduled_time\": \"11:10 PM\",\n" +
        "           \"service_place_name\": \"Telaprolu\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:10 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542012,\n" +
        "           \"scheduled_time\": \"11:15 PM\",\n" +
        "           \"service_place_name\": \"Gannavaram\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:15 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542007,\n" +
        "           \"scheduled_time\": \"11:30 PM\",\n" +
        "           \"service_place_name\": \"Ramavarapadu Ring\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:30 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542008,\n" +
        "           \"scheduled_time\": \"11:35 PM\",\n" +
        "           \"service_place_name\": \"Benz Circle\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:35 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542010,\n" +
        "           \"scheduled_time\": \"11:40 PM\",\n" +
        "           \"service_place_name\": \"Varadhi\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:40 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542009,\n" +
        "           \"scheduled_time\": \"11:45 PM\",\n" +
        "           \"service_place_name\": \"Tadepalli\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:45 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7541996,\n" +
        "           \"scheduled_time\": \"01:00 AM\",\n" +
        "           \"service_place_name\": \"Martur\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"01:00 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542056,\n" +
        "           \"scheduled_time\": \"01:30 AM\",\n" +
        "           \"service_place_name\": \"Medarmetla\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"01:30 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542043,\n" +
        "           \"scheduled_time\": \"02:00 AM\",\n" +
        "           \"service_place_name\": \"Ongole\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"02:00 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542045,\n" +
        "           \"scheduled_time\": \"03:00 AM\",\n" +
        "           \"service_place_name\": \"Chimakurthy \",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"03:00 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7541994,\n" +
        "           \"scheduled_time\": \"03:40 AM\",\n" +
        "           \"service_place_name\": \"Podili\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"03:40 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542013,\n" +
        "           \"scheduled_time\": \"11:00 PM\",\n" +
        "           \"service_place_name\": \"Hanuman junction\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:00 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542053,\n" +
        "           \"scheduled_time\": \"11:10 PM\",\n" +
        "           \"service_place_name\": \"Telaprolu\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:10 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542011,\n" +
        "           \"scheduled_time\": \"11:15 PM\",\n" +
        "           \"service_place_name\": \"Gannavaram\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:15 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542006,\n" +
        "           \"scheduled_time\": \"11:30 PM\",\n" +
        "           \"service_place_name\": \"Ramavarapadu Ring\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:30 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542003,\n" +
        "           \"scheduled_time\": \"11:35 PM\",\n" +
        "           \"service_place_name\": \"Benz Circle\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:35 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542005,\n" +
        "           \"scheduled_time\": \"11:40 PM\",\n" +
        "           \"service_place_name\": \"Varadhi\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:40 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542004,\n" +
        "           \"scheduled_time\": \"11:45 PM\",\n" +
        "           \"service_place_name\": \"Tadepalli\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:45 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542032,\n" +
        "           \"scheduled_time\": \"11:59 PM\",\n" +
        "           \"service_place_name\": \"Mangalagiri By pass\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"11:59 PM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542047,\n" +
        "           \"scheduled_time\": \"12:10 AM\",\n" +
        "           \"service_place_name\": \"Kakani\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"12:10 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 13164307,\n" +
        "           \"scheduled_time\": \"12:20 AM\",\n" +
        "           \"service_place_name\": \"Guntur Auto Nagar\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"12:20 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7542000,\n" +
        "           \"scheduled_time\": \"12:30 AM\",\n" +
        "           \"service_place_name\": \"Rtc Bus Stand\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"12:30 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7541997,\n" +
        "           \"scheduled_time\": \"12:45 AM\",\n" +
        "           \"service_place_name\": \"Chilakaluripet\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"12:45 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       },\n" +
        "       {\n" +
        "           \"geofence_name\": \"\",\n" +
        "           \"id\": 7541995,\n" +
        "           \"scheduled_time\": \"01:00 AM\",\n" +
        "           \"service_place_name\": \"Martur\",\n" +
        "           \"color\": \"color_gray\",\n" +
        "           \"delay_time\": null,\n" +
        "           \"expected_time\": \"01:00 AM\",\n" +
        "           \"skipped\": false,\n" +
        "           \"running_status\": null,\n" +
        "           \"is_pick_up\": 2,\n" +
        "           \"arrival_time\": \"\",\n" +
        "           \"departure_time\": \"\"\n" +
        "       }\n" +
        "   ],\n" +
        "   \"eta_pickup_data\": [],\n" +
        "   \"traveller_pickup_service_places\": null,\n" +
        "   \"current_sp_id\": 8627979,\n" +
        "   \"is_passed\": false,\n" +
        "   \"current_status_details\": {\n" +
        "       \"lat_long\": [\n" +
        "           16.316882777777778,\n" +
        "           80.48155\n" +
        "       ],\n" +
        "       \"details\": {\n" +
        "           \"speed\": 0,\n" +
        "           \"timestamp\": \"May 30 11:09:05\",\n" +
        "           \"location\": \"Takkellapadu, Pedakakani\",\n" +
        "           \"astl_id\": 1,\n" +
        "           \"class_name\": \"AssetTrackerLatest\"\n" +
        "       }\n" +
        "   },\n" +
        "   \"distance_details\": {},\n" +
        "   \"last_dropoff_id\": 7541985,\n" +
        "   \"dist_cur_and_second_next\": {},\n" +
        "   \"last_crossed_pickup\": null,\n" +
        "   \"is_passed_pickup\": null,\n" +
        "   \"stt_pickup_asset_name\": null,\n" +
        "   \"last_boarding_id\": 7541990\n }"