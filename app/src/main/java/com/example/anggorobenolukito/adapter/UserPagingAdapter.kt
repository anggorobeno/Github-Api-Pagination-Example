package com.example.anggorobenolukito.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.data.remote.response.ItemsItem
import com.example.anggorobenolukito.databinding.ItemListUserBinding

class UserPagingAdapter : PagingDataAdapter<ItemsItem, UserPagingAdapter.ViewHolder>(USER_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
        holder.itemView.setOnClickListener {
            if (user != null) {
                onItemClickCallback.onItemClicked(user)
            }
        }


    }

    inner class ViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .error(R.drawable.baseline_error_outline_black_24dp)
                    .into(ivAvatar)
                tvUserType.text = data.type
                tvUsername.text = data.login
            }
        }


    }


    companion object {
        private val USER_DIFF = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}