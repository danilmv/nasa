package com.andriod.nasa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private const val TAG = "@@ScrapYard"

    var currentDay = 0

    val numOfEpicPhotos = MutableLiveData<Int>()

    fun getFormattedDate(offset: Int = 0): String {
        val date = Calendar.getInstance().apply { add(Calendar.DATE, offset) }.time
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        Log.d(TAG, "getToday() called: offset = $offset today = $formattedDate")

        return formattedDate
    }
}