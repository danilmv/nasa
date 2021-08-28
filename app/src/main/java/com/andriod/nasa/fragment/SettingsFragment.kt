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
        fun setTheme(theme: Int)
        fun setDarkMode(isDarkMode: Boolean)
        fun getThemeId() : Int
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
            when(contract.getThemeId()){
                R.style.Theme_Nasa->defaultThemeChip.isChecked = true
                R.style.Theme_Nasa_Red->redThemeChip.isChecked = true
                R.style.Theme_Nasa_Green->greenThemeChip.isChecked = true
            }

            themesChipGroup.setOnCheckedChangeListener { _, _ ->
                contract.setTheme(
                    when {
                        defaultThemeChip.isChecked -> R.style.Theme_Nasa
                        redThemeChip.isChecked -> R.style.Theme_Nasa_Red
                        greenThemeChip.isChecked -> R.style.Theme_Nasa_Green
                        else -> R.style.Theme_Nasa

                    })
            }

            darkThemeChip.isChecked = contract.isDarkMode()
            darkThemeChip.setOnCheckedChangeListener{ _, isChecked ->
                contract.setDarkMode(isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}