package com.andriod.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.RetrofitHolder
import com.andriod.nasa.entity.Photo

class CuriosityPhotoViewModel : ViewModel() {

    private var _curiosity = MutableLiveData<List<Photo>>()
    val curiosity: LiveData<List<Photo>> = _curiosity

    fun interface Listener {
        fun onResponse(result: List<Photo>)
    }

    fun onCreate() {
        RetrofitHolder.requestCuriosity { photos -> _curiosity.postValue(photos) }
    }
}