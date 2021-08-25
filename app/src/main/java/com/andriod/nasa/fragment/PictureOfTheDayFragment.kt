package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.PictureViewModel
import com.andriod.nasa.databinding.FragmentPictureOfTheDayBinding
import com.bumptech.glide.Glide

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this).get(PictureViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.makeRequest()
        binding.apply {
            viewModel.pictureOfTheDay.observe(viewLifecycleOwner) { picture ->
                textView.text = picture.explanation

                if (picture.mediaType?.equals("image") == true) {
                    Glide.with(root)
                        .load(picture.hdurl)
                        .centerCrop()
                        .into(imageView)
                }
            }
        }
    }
}