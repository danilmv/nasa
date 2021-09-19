package com.andriod.nasa.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriod.nasa.R
import com.andriod.nasa.databinding.ItemEpicBinding
import com.andriod.nasa.entity.Epic
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class EpicRecyclerViewAdapter :
    RecyclerView.Adapter<EpicRecyclerViewAdapter.ViewHolder>() {
    private var epics = mutableListOf<Epic>()
    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun itemClicked(epic: Epic, view: View)
        fun loadCompleted()
    }

    inner class ViewHolder(
        parent: ViewGroup,
    ) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_epic, parent, false)) {

        private lateinit var epic: Epic
        private val binding = ItemEpicBinding.bind(itemView)

        init {
            listener?.let {
                binding.imageView.setOnClickListener {
                    listener?.itemClicked(epic, binding.imageView)
                }
            }
        }

        fun bind(epic: Epic) {
            this.epic = epic

            binding.imageView.transitionName = epic.imageUrl

            Glide.with(binding.root)
                .load(epic.imageUrl)
                .centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        loadCompleted()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        if (this@ViewHolder.epic.caption == epic.caption)
                            loadCompleted()
                        return false
                    }
                })
                .into(binding.imageView)
        }

        private fun loadCompleted() {
            listener?.loadCompleted()
        }
    }

    fun updateData(epics: List<Epic>) {
        this.epics.clear()
        this.epics.addAll(epics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(epics[position])
    }

    override fun getItemCount(): Int = epics.size

    companion object {
        const val TAG = "@@EpicRecyclerViewAdapter"
    }
}