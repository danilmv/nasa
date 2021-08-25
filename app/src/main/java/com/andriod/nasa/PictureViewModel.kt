package com.andriod.nasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureViewModel: ViewModel() {
    private var _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
    val pictureOfTheDay: LiveData<PictureOfTheDay> = _pictureOfTheDay
    private var isPictureRequested = false
    private val service by lazy { Utils.getNasaApiService() }

    fun makeRequest() {
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
                    isPictureRequested = false
                }
            })
        }
    }

}