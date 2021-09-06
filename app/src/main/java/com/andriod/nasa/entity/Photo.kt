package com.andriod.nasa.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val img_src: String,
    val earth_date: String,
) :Parcelable
