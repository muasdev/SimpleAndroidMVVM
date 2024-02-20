package com.muasdev.simpleandroidmvvm.ui.main.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muasdev.simpleandroidmvvm.databinding.ItemListPlaceholderBinding
import com.muasdev.simpleandroidmvvm.domain.model.Placeholder

class PlaceholderAdapter : PagingDataAdapter<Placeholder,
        PlaceholderAdapter.ItemViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListPlaceholderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    class ItemViewHolder(private var binding: ItemListPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Placeholder) {
            binding.tvAlbumId.text = "Album ID : ${user.albumId}"
            binding.tvId.text = "ID : ${user.id}"
            binding.tvTitle.text = "Title : ${user.title}"
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Placeholder>() {
            override fun areItemsTheSame(oldItem: Placeholder, newItem: Placeholder): Boolean =
                oldItem.albumId == newItem.albumId

            override fun areContentsTheSame(oldItem: Placeholder, newItem: Placeholder): Boolean =
                oldItem == newItem
        }
    }
}