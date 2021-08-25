package com.andriod.nasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.data.NasaApiService
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureViewModel : ViewModel() {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nasa.gov/")
            .build()
    }
    private val service: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }

    private var _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
    val pictureOfTheDay: LiveData<PictureOfTheDay> = _pictureOfTheDay
    private var isPictureRequested = false

    fun initialize() {
        if (!isPictureRequested) {
            service.getPictureOfTheDay().enqueue(object : Callback<PictureOfTheDay> {
                init {
                    isPictureRequested = true
                }

                override fun onResponse(
                    call: Call<PictureOfTheDay>,
                    response: Response<PictureOfTheDay>,
                ) {
                    if (response.isSuccessful) {
                        _pictureOfTheDay.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<PictureOfTheDay>, t: Throwable) {
                }
            })
        }
    }

}