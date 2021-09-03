package com.andriod.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.RetrofitHolder
import com.andriod.nasa.entity.Epic

class EpicViewModel : ViewModel() {
    private var _epic = MutableLiveData<List<Epic>>()
    val epic: LiveData<List<Epic>> = _epic

    fun interface Listener {
        fun onResponse(result: List<Epic>)
    }

    fun onCreate() {
        RetrofitHolder.requestEpic() { photos -> _epic.postValue(photos) }
    }
}