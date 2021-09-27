package com.andriod.nasa

import com.andriod.nasa.data.NasaApi
import com.andriod.nasa.entity.Curiosity
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.entity.PictureOfTheDay
import com.andriod.nasa.viewmodel.CuriosityPhotoViewModel
import com.andriod.nasa.viewmodel.EpicViewModel
import com.andriod.nasa.viewmodel.PictureViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHolder {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nasa.gov/")
            .build()
    }
    val nasaApi: NasaApi by lazy {
        retrofit.create(NasaApi::class.java)
    }

    private var pictureRequestedOffset = 1
    private val cachePicture = mutableMapOf<Int, PictureOfTheDay?>()

    private var isCuriosityRequested = false

    private var isEpicRequested = false
    private val epics = mutableListOf<Epic>()

    fun requestPicture(dayOffset: Int, listener: PictureViewModel.Listener) {
        cachePicture[dayOffset]?.let {
            listener.onResponse(it)
            return
        }
        if (pictureRequestedOffset != dayOffset) {
            pictureRequestedOffset = dayOffset
            nasaApi.getPictureOfTheDay(Utils.getFormattedDate(dayOffset))
                .enqueue(object : Callback<PictureOfTheDay> {
                    override fun onResponse(
                        call: Call<PictureOfTheDay>,
                        response: Response<PictureOfTheDay>,
                    ) {
                        if (response.isSuccessful) {
                            cachePicture[dayOffset] = response.body()!!
                            listener.onResponse(response.body())
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDay>, t: Throwable) {
                        pictureRequestedOffset = 1
                    }
                })
        }
    }

    fun requestCuriosity(listener: CuriosityPhotoViewModel.Listener) {
        if (!isCuriosityRequested) {
            isCuriosityRequested = true
            nasaApi.getCuriousityPhotos()
                .enqueue(object : Callback<Curiosity> {
                    override fun onResponse(
                        call: Call<Curiosity>,
                        response: Response<Curiosity>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                listener.onResponse(it.photos.toList())
                            }
                        } else {
                            isCuriosityRequested = false
                        }
                    }

                    override fun onFailure(call: Call<Curiosity>, t: Throwable) {
                        isCuriosityRequested = false
                    }
                })
        }
    }

    fun requestEpic(listener: EpicViewModel.Listener) {
        if (!isEpicRequested) {
            isEpicRequested = true
            nasaApi.getEpic()
                .enqueue(object : Callback<List<Epic>> {
                    override fun onResponse(
                        call: Call<List<Epic>>,
                        response: Response<List<Epic>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                epics.clear()
                                epics.addAll(it)
                                listener.onResponse(epics)
                            }
                        } else {
                            isEpicRequested = false
                        }
                    }

                    override fun onFailure(call: Call<List<Epic>>, t: Throwable) {
                        isEpicRequested = false
                    }
                })
        }else{
            listener.onResponse(epics)
        }
    }
}