package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andriod.nasa.MainActivity
import com.andriod.nasa.R
import com.andriod.nasa.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    private val contract: Contract by lazy { requireActivity() as MainActivity }

    interface Contract {
        fun setThemeId(theme: MainActivity.Companion.Themes)
        fun setDarkMode(isDarkMode: Boolean)
        fun getThemeId(): MainActivity.Companion.Themes
        fun isDarkMode(): Boolean
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            when (contract.getThemeId()) {
                MainActivity.Companion.Themes.DEFAULT -> defaultThemeChip.isChecked = true
                MainActivity.Companion.Themes.RED -> redThemeChip.isChecked = true
                MainActivity.Companion.Themes.GREEN -> greenThemeChip.isChecked = true
            }

            themesChipGroup.setOnCheckedChangeListener { _, _ ->
                contract.setThemeId(
                    when {
                        defaultThemeChip.isChecked -> MainActivity.Companion.Themes.DEFAULT
                        redThemeChip.isChecked -> MainActivity.Companion.Themes.RED
                        greenThemeChip.isChecked -> MainActivity.Companion.Themes.GREEN
                        else -> MainActivity.Companion.Themes.DEFAULT
                    })
            }

            darkThemeChip.isChecked = contract.isDarkMode()
            darkThemeChip.setOnCheckedChangeListener { _, isChecked ->
                contract.setDarkMode(isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}