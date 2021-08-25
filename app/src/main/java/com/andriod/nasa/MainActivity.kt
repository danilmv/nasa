package com.andriod.nasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy { ViewModelProvider(this).get(PictureViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.makeRequest()
        viewModel.pictureOfTheDay.observe(this) { picture ->
            binding.explanationTextView.text = picture.explanation
        }
    }
}