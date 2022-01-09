package com.example.anggorobenolukito.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.databinding.ItemListUserBinding

class FavouriteUserAdapter : ListAdapter<DetailUserEntity, FavouriteUserAdapter.ViewHolder>(
    USER_DIFF
) {
    lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getSwipedData(swipedPosition: Int): DetailUserEntity? = getItem(swipedPosition)

    companion object {
        private val USER_DIFF = object : DiffUtil.ItemCallback<DetailUserEntity>() {
            override fun areItemsTheSame(
                oldItem: DetailUserEntity,
                newItem: DetailUserEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DetailUserEntity,
                newItem: DetailUserEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DetailUserEntity) {
            with(binding) {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .into(ivAvatar)
                tvUsername.text = user.login
                tvUserType.text = user.type
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUserEntity)
    }
}