package com.andriod.nasa.entity

import com.google.gson.annotations.SerializedName

data class PictureOfTheDay(
    val date: String,
    val explanation: String,
    val hdurl: String,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String,
) {
    val isImage: Boolean get() = mediaType?.equals("image") == true
}
