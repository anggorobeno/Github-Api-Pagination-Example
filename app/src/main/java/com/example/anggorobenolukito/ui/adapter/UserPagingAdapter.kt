package com.example.anggorobenolukito.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.data.remote.response.UserResult
import com.example.anggorobenolukito.databinding.ItemListUserBinding

class UserPagingAdapter : PagingDataAdapter<UserResult, UserPagingAdapter.ViewHolder>(USER_DIFF) {
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
    }

    inner class ViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserResult) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .error(R.drawable.ic_failed)
                    .into(ivAvatar)
                tvUserType.text = data.type
                tvUsername.text = data.login
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }


    companion object {
        private val USER_DIFF = object : DiffUtil.ItemCallback<UserResult>() {
            override fun areItemsTheSame(oldItem: UserResult, newItem: UserResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserResult, newItem: UserResult): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResult)
    }

}