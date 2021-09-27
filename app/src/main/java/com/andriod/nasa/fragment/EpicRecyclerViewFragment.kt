package com.andriod.nasa.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.andriod.nasa.R
import com.andriod.nasa.adapter.EpicRecyclerViewAdapter
import com.andriod.nasa.databinding.FragmentRecyclerEpicBinding
import com.andriod.nasa.entity.Epic
import com.andriod.nasa.viewmodel.EpicViewModel

class EpicRecyclerViewFragment : Fragment() {
    private var _binding: FragmentRecyclerEpicBinding? = null
    private val binding: FragmentRecyclerEpicBinding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpicViewModel::class.java).also {
            it.onCreate()
        }
    }

    private val contract by lazy { requireActivity() as Contract }

    interface Contract {
        fun onItemClickListener(epic: Epic, view: View)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_image)

        postponeEnterTransition()

        _binding = FragmentRecyclerEpicBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = EpicRecyclerViewAdapter().also { adapter ->
                viewModel.epic.observe(viewLifecycleOwner) {
                    adapter.updateData(it)
                }
                adapter.listener = object : EpicRecyclerViewAdapter.OnItemClickListener {
                    override fun itemClicked(epic: Epic, view: View) =
                        contract.onItemClickListener(epic, view)

                    override fun loadCompleted() {
                        startPostponedEnterTransition()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}