package com.andriod.nasa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriod.nasa.databinding.ItemEpicBinding
import com.andriod.nasa.entity.Epic
import com.bumptech.glide.Glide

class EpicRecyclerViewAdapter : RecyclerView.Adapter<EpicRecyclerViewAdapter.ViewHolder>() {
    private var epics = mutableListOf<Epic>()
    var listener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun itemClicked(epic: Epic)
    }

    class ViewHolder(val binding: ItemEpicBinding, private val listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var epic: Epic

        init {
            listener?.let { binding.imageView.setOnClickListener { listener.itemClicked(epic) } }
        }

        fun bind(epic: Epic) {
            Glide.with(binding.root)
                .load(epic.imageUrl)
                .centerCrop()
                .into(binding.imageView)

            this.epic = epic
        }
    }

    fun updateData(epics: List<Epic>) {
        this.epics.clear()
        this.epics.addAll(epics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemEpicBinding.inflate(LayoutInflater.from(parent.context)), listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(epics[position])

    override fun getItemCount(): Int = epics.size
}