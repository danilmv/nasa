package com.andriod.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriod.nasa.RetrofitHolder
import com.andriod.nasa.entity.Epic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpicViewModel : ViewModel() {
    private val nasaApi by lazy { RetrofitHolder.nasaApi }

    private var _epic = MutableLiveData<List<Epic>>()
    val epic: LiveData<List<Epic>> = _epic

    private var isRequested = false

    fun onCreate() {
        if (!isRequested) {
            isRequested = true
            nasaApi.getEpic()
                .enqueue(object : Callback<List<Epic>> {
                    override fun onResponse(
                        call: Call<List<Epic>>,
                        response: Response<List<Epic>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                _epic.postValue(it.toList())
                            }
                        } else {
                            isRequested = false
                        }
                    }

                    override fun onFailure(call: Call<List<Epic>>, t: Throwable) {
                        isRequested = false
                    }
                })
        }
    }
}