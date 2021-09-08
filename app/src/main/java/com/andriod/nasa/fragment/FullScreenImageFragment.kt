package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.andriod.nasa.databinding.FragmentImageFullScreenBinding
import com.andriod.nasa.entity.Epic
import com.bumptech.glide.Glide

class FullScreenImageFragment : Fragment() {
    private var _binding: FragmentImageFullScreenBinding? = null
    private val binding: FragmentImageFullScreenBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentImageFullScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epic = arguments?.getParcelable<Epic>(ARGUMENT_KEY_EPIC)
        epic?.let {
            Glide.with(binding.root)
                .load(epic.imageUrl)
                .fitCenter()
                .into(binding.imageView)
        }
        resizeImage()
    }

    private fun resizeImage() {
        binding.imageView.apply {
            setOnClickListener {
                TransitionManager.beginDelayedTransition(
                    binding.container,
                    TransitionSet()
                        .addTransition(ChangeImageTransform())
                )

                scaleType = when (scaleType) {
                    ImageView.ScaleType.CENTER_CROP -> ImageView.ScaleType.CENTER
                    else -> ImageView.ScaleType.CENTER_CROP
                }
            }
        }
    }

    companion object {
        private const val ARGUMENT_KEY_EPIC = "epic"
        fun newInstance(epic: Epic) = FullScreenImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARGUMENT_KEY_EPIC, epic)
            }
        }
    }
}