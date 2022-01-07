package com.example.anggorobenolukito.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.data.remote.response.ItemsItem
import com.example.anggorobenolukito.databinding.ItemListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var listUser = ArrayList<ItemsItem>()


    fun setListUser(data: List<ItemsItem>?){
        Log.d("TAG", "setListUser: $listUser")
//        if (data == null) return
        this.listUser.clear()
        if (data != null) {
            Log.d("TAG", "setListUser: $listUser")
            this.listUser.addAll(data)
        }
    }
    inner class ViewHolder(private val binding : ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : ItemsItem){
            Glide.with(itemView)
                .load(data.avatarUrl)
                .into(binding.ivAvatar)
            binding.tvUsername.text = data.login
            binding.tvUserType.text = data.type
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
       return listUser.size
    }
}