package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andriod.nasa.databinding.FragmentEpicBinding
import com.andriod.nasa.entity.Epic
import com.bumptech.glide.Glide

private const val ARG_PARAM_EPIC = "epic"

class EpicFragment : Fragment() {
    private var epic: Epic? = null

    private var _binding: FragmentEpicBinding? = null
    private val binding: FragmentEpicBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            epic = it.getParcelable<Epic>(ARG_PARAM_EPIC)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEpicBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        epic?.let {
            binding.apply {
                textViewCaption.text = "${it.caption}: ${it.date}"

                Glide.with(root)
                    .load(it.imageUrl)
                    .centerCrop()
                    .into(imageView)
            }
        }
    }

    companion object {
        fun newInstance(epic: Epic) =
            EpicFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_EPIC, epic)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}