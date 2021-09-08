package com.andriod.nasa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriod.nasa.databinding.ItemEpicBinding
import com.andriod.nasa.entity.Epic
import com.bumptech.glide.Glide

class EpicRecyclerViewAdapter : RecyclerView.Adapter<EpicRecyclerViewAdapter.ViewHolder>() {

    private lateinit var binding: ItemEpicBinding
    private var epics = mutableListOf<Epic>()

    class ViewHolder(val binding: ItemEpicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(epic: Epic) {
            Glide.with(binding.root)
                .load(epic.imageUrl)
//                    .placeholder(binding.imageViewPoster.drawable)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    fun updateData(epics: List<Epic>){
        this.epics.clear()
        this.epics.addAll(epics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemEpicBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(epics[position])

    override fun getItemCount(): Int = epics.size
}