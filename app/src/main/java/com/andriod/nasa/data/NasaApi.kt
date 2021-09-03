package com.andriod.nasa.data

import com.andriod.nasa.BuildConfig
import com.andriod.nasa.Utils
import com.andriod.nasa.entity.Curiosity
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/planetary/apod")
    fun getPictureOfTheDay(
        @Query("date") date: String = Utils.getFormattedDate(),
        @Query("api_key") apiKey: String = BuildConfig.ApiKey,
    ): Call<PictureOfTheDay>

    @GET("/EPIC/api/natural/images")
    fun getEpic(@Query("api_key") apiKey: String = BuildConfig.ApiKey): Call<List<Epic>>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos?sol=1000")
    fun getCuriousityPhotos(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey,
    ): Call<Curiosity>
}