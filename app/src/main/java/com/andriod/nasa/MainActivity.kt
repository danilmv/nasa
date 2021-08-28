package com.andriod.nasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.databinding.ActivityMainBinding
import com.andriod.nasa.fragment.PictureOfTheDayFragment
import com.andriod.nasa.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val fragmentPictureOfTheDay by lazy { PictureOfTheDayFragment() }
    private val fragmentSettings by lazy { SettingsFragment() }

    private val bottomView: BottomNavigationView by lazy { binding.bottomView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragmentPictureOfTheDay)
            .commit()

        prepareBottomNavigationView()
    }

    private fun prepareBottomNavigationView() {
        bottomView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.menu_bottom_item_picture->{
                    showPicture()
                }
                R.id.menu_bottom_item_settings->{
                    showSettings()
                }
                else->{ return@setOnItemSelectedListener false}
            }
            true
        }
    }

    private fun showPicture() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragmentPictureOfTheDay)
            .commit()
    }

    private fun showSettings() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragmentSettings)
            .commit()
    }

}