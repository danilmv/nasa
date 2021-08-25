package com.andriod.nasa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureViewModel : ViewModel() {
    private var _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
    val pictureOfTheDay: LiveData<PictureOfTheDay> = _pictureOfTheDay
    private var pictureRequestedOffset = 1
    private val service by lazy { Utils.getNasaApiService() }

    fun requestPicture(offset: Int = 0) {
        if (pictureRequestedOffset != offset) {
            pictureRequestedOffset = offset
            service.getPictureOfTheDay(Utils.getToday(offset))
                .enqueue(object : Callback<PictureOfTheDay> {
                    override fun onResponse(
                        call: Call<PictureOfTheDay>,
                        response: Response<PictureOfTheDay>,
                    ) {
                        if (response.isSuccessful) {
                            _pictureOfTheDay.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDay>, t: Throwable) {
                        pictureRequestedOffset = 1
                    }
                })
        }
    }
}