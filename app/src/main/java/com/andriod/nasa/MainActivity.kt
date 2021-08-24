package com.andriod.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andriod.nasa.data.PictureOfTheDayService
import com.andriod.nasa.databinding.ActivityMainBinding
import com.andriod.nasa.entity.PictureOfTheDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nasa.gov/")
            .build()
    }
    private val service: PictureOfTheDayService by lazy {
        retrofit.create(PictureOfTheDayService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service.getPicture().enqueue(object : Callback<PictureOfTheDay> {
            override fun onResponse(
                call: Call<PictureOfTheDay>,
                response: Response<PictureOfTheDay>,
            ) {
                if (response.isSuccessful){
                    binding.textView.text = response.body()?.explanation
                }
            }

            override fun onFailure(call: Call<PictureOfTheDay>, t: Throwable) {
            }
        })
    }
}