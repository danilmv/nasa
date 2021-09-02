package com.andriod.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.RetrofitHolder
import com.andriod.nasa.Utils
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureViewModel : ViewModel() {
    private var _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
    val pictureOfTheDay: LiveData<PictureOfTheDay> = _pictureOfTheDay
    private var pictureRequestedOffset = 1
    private val nasaApi by lazy { RetrofitHolder.nasaApi }

    private val cachePicture = mutableMapOf<Int, PictureOfTheDay?>()

    fun onDateChanged(offset: Int = 0){
        requestPicture(offset)
    }

    private fun requestPicture(offset: Int = 0) {
        if (cachePicture[offset] != null) {
            _pictureOfTheDay.postValue(cachePicture[offset])
            return
        }
        if (pictureRequestedOffset != offset) {
            pictureRequestedOffset = offset
            nasaApi.getPictureOfTheDay(Utils.getFormattedDate(offset - 8))
                .enqueue(object : Callback<PictureOfTheDay> {
                    override fun onResponse(
                        call: Call<PictureOfTheDay>,
                        response: Response<PictureOfTheDay>,
                    ) {
                        if (response.isSuccessful) {
                            cachePicture[offset] = response.body()!!
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