package com.andriod.nasa.data

import com.andriod.nasa.BuildConfig
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.http.GET

interface NasaApiService {
 @GET("planetary/apod?api_key=${BuildConfig.ApiKey}")
 fun getPictureOfTheDay(): Call<PictureOfTheDay>
}