package com.andriod.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.RetrofitHolder
import com.andriod.nasa.entity.PictureOfTheDay

class PictureViewModel : ViewModel() {
    private var _pictureOfTheDay = MutableLiveData<PictureOfTheDay>()
    val pictureOfTheDay: LiveData<PictureOfTheDay> = _pictureOfTheDay

    fun interface Listener{
        fun onResponse(result: PictureOfTheDay?)
    }

    fun onDateChanged(offset: Int = 0){
        RetrofitHolder.requestPicture(offset){ picture -> _pictureOfTheDay.postValue(picture)}
    }
}