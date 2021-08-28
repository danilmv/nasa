package com.andriod.nasa

import android.util.Log
import com.andriod.nasa.data.NasaApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private const val TAG = "@@Utils"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nasa.gov/")
            .build()
    }
    private val service: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }

    fun getNasaApiService() = service

    fun getToday(offset: Int = 0): String {
        val date = Calendar.getInstance().apply { add(Calendar.DATE, offset) }.time
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        Log.d(TAG, "getToday() called: offset = $offset today = $today")

        return today
    }
}