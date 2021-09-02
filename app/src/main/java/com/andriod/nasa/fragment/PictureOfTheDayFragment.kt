package com.andriod.nasa.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.viewmodel.PictureViewModel
import com.andriod.nasa.Utils
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
                } else {
                    webView.webViewClient = WebViewClient()
                    webView.setInitialScale(70)
                    webView.settings.loadWithOverviewMode = true
                    webView.settings.useWideViewPort = true
                    webView.loadUrl(picture.url)
                }
                imageView.isVisible = picture.isImage
                webView.isVisible = !picture.isImage
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
                Utils.currentDay = when {
                    todayChip.isChecked -> TODAY
                    yesterdayChip.isChecked -> YESTERDAY
                    beforeYesterdayChip.isChecked -> BEFORE_YESTERDAY
                    else -> TODAY
                }
                viewModel.onDateChanged(Utils.currentDay)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            when (Utils.currentDay) {
                TODAY -> todayChip.isChecked = true
                YESTERDAY -> yesterdayChip.isChecked = true
                BEFORE_YESTERDAY -> beforeYesterdayChip.isChecked = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "@@PictureFragment"

        const val TODAY = 0
        const val YESTERDAY = -1
        const val BEFORE_YESTERDAY = -2
    }
}