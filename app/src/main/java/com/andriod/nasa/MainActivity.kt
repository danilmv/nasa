package com.andriod.nasa

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import com.andriod.nasa.databinding.ActivityMainBinding
import com.andriod.nasa.fragment.PictureOfTheDayFragment
import com.andriod.nasa.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), SettingsFragment.Contract {
    private lateinit var binding: ActivityMainBinding

    private val fragmentPictureOfTheDay by lazy { PictureOfTheDayFragment() }
    private val fragmentSettings by lazy { SettingsFragment() }

    private var currentFragment: FragmentTags = FragmentTags.PICTURE
    private var currentTheme = R.style.Theme_Nasa

    private val bottomView: BottomNavigationView by lazy { binding.bottomView }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            currentFragment = FragmentTags.valueOf(it.getString(KEY_CURRENT_FRAGMENT) ?: "PICTURE")
            currentTheme = it.getInt(KEY_CURRENT_THEME)
        }

        setTheme(currentTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareBottomNavigationView()
        showCurrentFragment()
    }

    private fun prepareBottomNavigationView() {
        bottomView.setOnItemSelectedListener {
            currentFragment = when (it.itemId) {
                R.id.menu_bottom_item_picture -> {
                    FragmentTags.PICTURE
                }
                R.id.menu_bottom_item_settings -> {
                    FragmentTags.SETTINGS
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
            showCurrentFragment()
            true
        }
    }

    private fun showCurrentFragment() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,
                when (currentFragment) {
                    FragmentTags.PICTURE -> fragmentPictureOfTheDay
                    FragmentTags.SETTINGS -> fragmentSettings
                })
            .commit()
    }

    override fun changeTheme(theme: Int) {
        currentTheme = theme
        recreate()
    }

    override fun changeDarkMode(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun requestTheme(): Int {
        return currentTheme
    }

    override fun requestDarkMode(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    companion object {
        enum class FragmentTags(val id: Int) {
            PICTURE(0), SETTINGS(1),
        }

        const val TAG = "@@MainActivity"
        const val KEY_CURRENT_FRAGMENT = "current_fragment"
        const val KEY_CURRENT_THEME = "current_theme"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState() called with: outState = $outState")
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_FRAGMENT, currentFragment.name)
        outState.putInt(KEY_CURRENT_THEME, currentTheme)
    }
}