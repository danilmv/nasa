package com.andriod.nasa

import com.andriod.nasa.data.NasaApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHolder {

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
}