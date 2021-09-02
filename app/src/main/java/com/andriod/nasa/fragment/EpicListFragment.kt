package com.andriod.nasa.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.andriod.nasa.Utils
import com.andriod.nasa.adapter.EpicListViewPagerAdapter
import com.andriod.nasa.databinding.FragmentEpicListBinding
import com.andriod.nasa.viewmodel.EpicViewModel

class EpicListFragment : Fragment() {
    private var _binding: FragmentEpicListBinding? = null
    private val binding: FragmentEpicListBinding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpicViewModel::class.java).also {
            it.onCreate()
        }
    }

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
            Utils.numOfEpicPhotos.value = it.size
            binding.viewPager.setPageTransformer { page, position ->
                page.alpha = 1 - position
                page.scaleY = position + 1
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}