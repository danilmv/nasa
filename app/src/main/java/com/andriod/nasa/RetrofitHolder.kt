package com.andriod.nasa

import com.andriod.nasa.data.NasaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHolder {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nasa.gov/")
            .build()
    }
    val nasaApi: NasaApi by lazy {
        retrofit.create(NasaApi::class.java)
    }
}