package com.andriod.nasa.entity

import com.google.gson.annotations.SerializedName

data class PictureOfTheDay(
    val date: String,
    val explanation: String,
    val hdurl: String,
    @SerializedName("service_name")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String,
)
