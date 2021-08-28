package com.andriod.nasa

import android.os.Build
import android.os.Bundle
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
    private var currentDarkMode = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    private val bottomView: BottomNavigationView by lazy { binding.bottomView }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            currentFragment = FragmentTags.valueOf(it.getString(KEY_CURRENT_FRAGMENT) ?: "PICTURE")
            currentTheme = it.getInt(KEY_CURRENT_THEME)
            currentDarkMode = it.getInt(KEY_CURRENT_DARK_MODE) == 1
        }

        setTheme(currentTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeDarkMode(currentDarkMode)

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
        currentDarkMode = isDark
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun requestTheme(): Int {
        return currentTheme
    }

    override fun requestDarkMode(): Boolean {
        return currentDarkMode
    }

    companion object {
        enum class FragmentTags(val id: Int) {
            PICTURE(0), SETTINGS(1),
        }

        const val TAG = "@@MainActivity"
        const val KEY_CURRENT_FRAGMENT = "current_fragment"
        const val KEY_CURRENT_THEME = "current_theme"
        const val KEY_CURRENT_DARK_MODE = "current_dark_mode"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_FRAGMENT, currentFragment.name)
        outState.putInt(KEY_CURRENT_THEME, currentTheme)
        outState.putInt(KEY_CURRENT_DARK_MODE, if(currentDarkMode) 1 else 0)
    }
}