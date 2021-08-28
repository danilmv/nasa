package com.andriod.nasa

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object ScrapYard {
    private const val TAG = "@@ScrapYard"

    var currentDay = 0

    fun getFormattedDate(offset: Int = 0): String {
        val date = Calendar.getInstance().apply { add(Calendar.DATE, offset) }.time
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        Log.d(TAG, "getToday() called: offset = $offset today = $formattedDate")

        return formattedDate
    }
}