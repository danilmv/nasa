package com.andriod.nasa.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Epic(
    val caption: String,
    val image: String,
    val date: String,
) : Parcelable {
    private val year: String get() = date.substring(0, 4)
    private val month: String get() = date.substring(5, 7)
    private val day: String get() = date.substring(8, 10)
    val imageUrl: String get() = "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/png/$image.png"
}
