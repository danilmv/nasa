package com.andriod.nasa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.entity.Photo
import com.andriod.nasa.fragment.CuriosityPhotoFragment
import com.andriod.nasa.fragment.EpicFragment

class CuriosityPhotoListViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val curiosityPhotos: List<Photo>,
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = curiosityPhotos.size

    override fun createFragment(position: Int): Fragment = CuriosityPhotoFragment.newInstance(curiosityPhotos[position])
}