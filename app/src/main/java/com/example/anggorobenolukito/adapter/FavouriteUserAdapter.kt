package com.example.anggorobenolukito.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.databinding.ItemListUserBinding

class FavouriteUserAdapter : RecyclerView.Adapter<FavouriteUserAdapter.ViewHolder>() {
    private val listFavouriteUser = ArrayList<DetailUserEntity>()
    lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setFavouriteUser(data: List<DetailUserEntity>) {
        listFavouriteUser.clear()
        listFavouriteUser.addAll(data)
        notifyDataSetChanged()

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
        val user = listFavouriteUser[position]
        holder.bind(user)
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(user)
//        }
    }

    override fun getItemCount(): Int {
        return listFavouriteUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUserEntity)
    }
}