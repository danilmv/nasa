package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andriod.nasa.R
import com.andriod.nasa.adapter.MainViewPagerAdapter
import com.andriod.nasa.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding get() = _binding!!

    private val pages by lazy {
        listOf(
            MainViewPagerAdapter.Page(getString(R.string.name_picture)) { PictureOfTheDayFragment() },
            MainViewPagerAdapter.Page(getString(R.string.name_epic)) { EpicListFragment() },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = MainViewPagerAdapter(requireActivity(), pages)

        TabLayoutMediator(binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = pages[position].name
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}