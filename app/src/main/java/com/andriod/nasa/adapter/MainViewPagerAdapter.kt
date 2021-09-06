package com.andriod.nasa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout

class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val pages: List<Page>,
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position].constructor()

    data class Page(
        val name: String,
        val constructor: () -> Fragment,
        val setBadge: (TabLayout.Tab) -> Unit = {},
    )
}
