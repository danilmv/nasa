package com.andriod.nasa.entity

data class Epic(
    val caption: String,
    val image: String,
    val date: String,
) {
    private val year: String get() = date.substring(0, 3)
    private val month: String get() = date.substring(4, 5)
    private val day: String get() = date.substring(6, 7)
    val imageUrl: String get() = "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/png/$image.png"
}
