package com.andriod.nasa.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.StrikethroughSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.R
import com.andriod.nasa.SplashActivity
import com.andriod.nasa.Utils
import com.andriod.nasa.databinding.FragmentPictureOfTheDayBinding
import com.andriod.nasa.viewmodel.PictureViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this).get(PictureViewModel::class.java) }

    private var isFirstStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        if (Utils.isFirstStart) {
            Utils.isFirstStart = false
            requireActivity().startActivity(Intent(context, SplashActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.pictureOfTheDay.observe(viewLifecycleOwner) { picture ->
                val spannable = SpannableString(picture.explanation).apply {
                    val typedValue = TypedValue()
                    requireActivity().theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)

                    setSpan(
                        BulletSpan(20, typedValue.data),
                        0, length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    setSpan(BackgroundColorSpan(R.color.pink),
                        0,
                        50,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

                    setSpan(StrikethroughSpan(),
                        51,
                        100,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

                    val myTypeface =
                        Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.alexbrush),
                            Typeface.BOLD)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        setSpan(TypefaceSpan(myTypeface),
                            101,
                            200,
                            Spannable.SPAN_COMPOSING)
                    }
                }
                textView.text = spannable

                if (picture.isImage) {
                    Glide.with(root)
                        .load(picture.url)
                        .centerCrop()
                        .into(imageView)
                } else {
                    callYoutubeButton.setOnClickListener {
                        Intent.createChooser(
                            Intent(Intent.ACTION_VIEW, Uri.parse(picture.url)),
                            getString(R.string.pick_app_to_play_video))
                            ?.let { startActivity(it) }
                    }
                }
                imageView.isVisible = picture.isImage
                callYoutubeButton.isVisible = !picture.isImage
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