package com.andriod.nasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andriod.nasa.adapter.EpicListViewPagerAdapter
import com.andriod.nasa.databinding.FragmentEpicListBinding
import com.andriod.nasa.viewmodel.EpicViewModel

class EpicListFragment : Fragment() {
    private var _binding: FragmentEpicListBinding? = null
    private val binding: FragmentEpicListBinding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this).get(EpicViewModel::class.java).also {
        it.onCreate()
    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEpicListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.epic.observe(viewLifecycleOwner) {
            binding.viewPager.adapter = EpicListViewPagerAdapter(requireActivity(), it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}