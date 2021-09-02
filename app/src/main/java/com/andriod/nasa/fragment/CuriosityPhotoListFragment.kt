package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.Utils
import com.andriod.nasa.adapter.CuriosityPhotoListViewPagerAdapter
import com.andriod.nasa.databinding.FragmentCuriosityPhotoListBinding
import com.andriod.nasa.viewmodel.CuriosityPhotoViewModel

class CuriosityPhotoListFragment : Fragment() {
    private var _binding: FragmentCuriosityPhotoListBinding? = null
    private val binding: FragmentCuriosityPhotoListBinding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(CuriosityPhotoViewModel::class.java).apply { onCreate() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCuriosityPhotoListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.curiosity.observe(viewLifecycleOwner) { photos ->
            binding.viewPager.adapter =
                CuriosityPhotoListViewPagerAdapter(requireActivity(), photos)
            Utils.numOfCuriosityPhotos.value = photos.size
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}