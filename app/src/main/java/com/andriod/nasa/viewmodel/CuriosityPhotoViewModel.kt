package com.andriod.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.RetrofitHolder
import com.andriod.nasa.entity.Curiosity
import com.andriod.nasa.entity.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuriosityPhotoViewModel : ViewModel() {
    private val nasaApi by lazy { RetrofitHolder.nasaApi }

    private var _curiosity = MutableLiveData<List<Photo>>()
    val curiosity: LiveData<List<Photo>> = _curiosity

    private var isRequested = false

    fun onCreate() {
        if (!isRequested) {
            isRequested = true
            nasaApi.getCuriousityPhotos()
                .enqueue(object : Callback<Curiosity> {
                    override fun onResponse(
                        call: Call<Curiosity>,
                        response: Response<Curiosity>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                _curiosity.postValue(it.photos.toList())
                            }
                        } else {
                            isRequested = false
                        }
                    }

                    override fun onFailure(call: Call<Curiosity>, t: Throwable) {
                        isRequested = false
                    }
                })
        }
    }
}