package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.andriod.nasa.R
import com.andriod.nasa.Utils
import com.andriod.nasa.adapter.MainViewPagerAdapter
import com.andriod.nasa.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding get() = _binding!!

    private val setSetBadge = { liveData: MutableLiveData<Int> ->
        { tab: TabLayout.Tab ->
            liveData.observe(viewLifecycleOwner) {
                tab.orCreateBadge.number = it
            }
        }
    }
    private val pages by lazy {
        listOf(
            MainViewPagerAdapter.Page(
                getString(R.string.name_picture),
                { PictureOfTheDayFragment() }),
            MainViewPagerAdapter.Page(
                getString(R.string.name_epic),
                { EpicListFragment() },
                setSetBadge(Utils.numOfEpicPhotos)),
            MainViewPagerAdapter.Page(
                getString(R.string.name_curiosity),
                { CuriosityPhotoListFragment() },
                setSetBadge(Utils.numOfCuriosityPhotos)),
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
            pages[position].setBadge(tab)
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}