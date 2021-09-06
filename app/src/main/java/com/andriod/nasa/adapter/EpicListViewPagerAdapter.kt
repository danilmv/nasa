package com.andriod.nasa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.fragment.EpicFragment

class EpicListViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val epics: List<Epic>,
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = epics.size

    override fun createFragment(position: Int): Fragment = EpicFragment.newInstance(epics[position])
}