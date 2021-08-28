package com.andriod.nasa.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.PictureViewModel
import com.andriod.nasa.ScrapYard
import com.andriod.nasa.databinding.FragmentPictureOfTheDayBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior

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

        binding.apply {
            viewModel.pictureOfTheDay.observe(viewLifecycleOwner) { picture ->
                textView.text = picture.explanation

                if (picture.isImage) {
                    Glide.with(root)
                        .load(picture.url)
                        .centerCrop()
                        .into(imageView)
                }
                imageView.isVisible = picture.isImage
            }

            val bottomSheetBehavior: BottomSheetBehavior<FrameLayout> =
                BottomSheetBehavior.from(bottomSheetFrameLayout)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetFrameLayout.setOnClickListener {
                bottomSheetBehavior.state = when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.STATE_EXPANDED -> BottomSheetBehavior.STATE_COLLAPSED
                    else -> BottomSheetBehavior.STATE_EXPANDED
                }

                Log.d(TAG, "onViewCreated() called state = ${bottomSheetBehavior.state}")
            }

            chipGroup.setOnCheckedChangeListener { _, _ ->
                ScrapYard.currentDay = when {
                    todayChip.isChecked -> 0
                    yesterdayChip.isChecked -> -1
                    beforeYesterdayChip.isChecked -> -2
                    else -> 0
                }
                viewModel.onDateChanged(ScrapYard.currentDay)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            when (ScrapYard.currentDay) {
                0 -> todayChip.isChecked = true
                -1 -> yesterdayChip.isChecked = true
                -2 -> beforeYesterdayChip.isChecked = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "@@PictureFragment"
    }
}