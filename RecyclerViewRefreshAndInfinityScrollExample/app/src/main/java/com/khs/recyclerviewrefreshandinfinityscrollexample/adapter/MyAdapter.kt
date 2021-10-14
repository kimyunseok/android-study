package com.khs.recyclerviewrefreshandinfinityscrollexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.recyclerviewrefreshandinfinityscrollexample.databinding.HolderRecyclerviewBinding
import com.khs.recyclerviewrefreshandinfinityscrollexample.model.RecyclerModel

class MyAdapter(context: Context, val itemList: MutableList<RecyclerModel>): RecyclerView.Adapter<MyAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HolderRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(val binding: HolderRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecyclerModel) {
            binding.model = item
        }
    }

}