package com.andriod.nasa.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andriod.nasa.entity.Epic

class EpicListViewPagerAdapter(fragmentActivity: FragmentActivity, private val epics: List<Epic>):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = epics.size

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}