package com.example.weatherapp.models

data class ResponseData(
    val message: String,
    val user_id: Int,
    val name: String,
    val email: String,
    val mobile: Int,
    val profile_details: ProfileDetails,
    val data_list: ArrayList<DataListDetail>
)
