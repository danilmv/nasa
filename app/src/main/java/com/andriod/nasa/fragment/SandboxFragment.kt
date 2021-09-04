package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andriod.nasa.databinding.FragmentSandboxBinding

class SandboxFragment:Fragment() {
    private var _binding: FragmentSandboxBinding? = null
    private val binding:FragmentSandboxBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSandboxBinding.inflate(inflater)
        return binding.root
    }
}