package com.andriod.nasa

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import com.andriod.nasa.databinding.ActivityMainBinding
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.fragment.EpicRecyclerViewFragment
import com.andriod.nasa.fragment.MainViewPagerFragment
import com.andriod.nasa.fragment.PictureOfTheDayFragment
import com.andriod.nasa.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), SettingsFragment.Contract,
    EpicRecyclerViewFragment.Contract {
    private lateinit var binding: ActivityMainBinding

    private val fragmentPictureOfTheDay by lazy { PictureOfTheDayFragment() }
    private val fragmentSettings by lazy { SettingsFragment() }
    private val fragmentViewPager by lazy { MainViewPagerFragment() }

    private var currentFragment: FragmentTags = FragmentTags.VIEW_PAGER
    private var currentTheme = Themes.DEFAULT
    private var isDarkMode = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    private val bottomView: BottomNavigationView by lazy { binding.bottomView }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            currentFragment = FragmentTags.valueOf(it.getString(KEY_CURRENT_FRAGMENT)
                ?: PICTURE_FRAGMENT_TAG_NAME)
            currentTheme = Themes.valueOf(it.getString(KEY_CURRENT_THEME) ?: Themes.DEFAULT.name)
            isDarkMode = it.getInt(KEY_CURRENT_DARK_MODE) == 1
        }

        setTheme(currentTheme.id)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDarkMode(isDarkMode)

        prepareBottomNavigationView()
        showCurrentFragment()
    }


    private fun prepareBottomNavigationView() {
        bottomView.setOnItemSelectedListener {
            currentFragment = when (it.itemId) {
                R.id.menu_bottom_item_picture -> {
                    FragmentTags.VIEW_PAGER
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
                    FragmentTags.VIEW_PAGER -> fragmentViewPager
                })
            .commit()
    }

    override fun setThemeId(theme: Themes) {
        currentTheme = theme
        recreate()
    }


    override fun setDarkMode(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun getThemeId() = currentTheme

    override fun isDarkMode() = isDarkMode

    companion object {
        enum class FragmentTags(val id: Int) {
            PICTURE(0), SETTINGS(1), VIEW_PAGER(2)
        }

        const val TAG = "@@MainActivity"
        const val KEY_CURRENT_FRAGMENT = "current_fragment"
        const val KEY_CURRENT_THEME = "current_theme"
        const val KEY_CURRENT_DARK_MODE = "current_dark_mode"

        const val PICTURE_FRAGMENT_TAG_NAME = "PICTURE"

        enum class Themes(val id: Int) {
            DEFAULT(R.style.Theme_Nasa), RED(R.style.Theme_Nasa_Red), GREEN(R.style.Theme_Nasa_Green)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_FRAGMENT, currentFragment.name)
        outState.putString(KEY_CURRENT_THEME, currentTheme.name)
        outState.putInt(KEY_CURRENT_DARK_MODE, if (isDarkMode) 1 else 0)
    }

    override fun onItemClickListener(epic: Epic) {
        Toast.makeText(this, "item clicked: ${epic.caption}", Toast.LENGTH_SHORT).show()
    }
}