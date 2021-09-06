package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andriod.nasa.databinding.FragmentCuriosityPhotoBinding
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.entity.Photo
import com.bumptech.glide.Glide

private const val ARG_PARAM_CURIOSITY = "curiosity"

class CuriosityPhotoFragment : Fragment() {
    private var curiosityPhoto: Photo? = null

    private var _binding: FragmentCuriosityPhotoBinding? = null
    private val binding: FragmentCuriosityPhotoBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            curiosityPhoto = it.getParcelable<Photo>(ARG_PARAM_CURIOSITY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCuriosityPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        curiosityPhoto?.let {
            binding.apply {
                textViewCaption.text = it.earth_date

                Glide.with(root)
                    .load(it.img_src)
                    .centerCrop()
                    .into(imageView)
            }
        }
    }

    companion object {
        fun newInstance(curiosityPhoto: Photo) =
            CuriosityPhotoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_CURIOSITY, curiosityPhoto)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}