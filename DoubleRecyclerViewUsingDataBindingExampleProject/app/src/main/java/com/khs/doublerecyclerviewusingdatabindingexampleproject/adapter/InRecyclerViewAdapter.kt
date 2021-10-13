package com.khs.doublerecyclerviewusingdatabindingexampleproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.doublerecyclerviewusingdatabindingexampleproject.databinding.HolderRecyclerviewInBinding
import com.khs.doublerecyclerviewusingdatabindingexampleproject.model.RecyclerInViewModel

class InRecyclerViewAdapter(context: Context, val itemList: MutableList<RecyclerInViewModel>): RecyclerView.Adapter<InRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HolderRecyclerviewInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: HolderRecyclerviewInBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecyclerInViewModel) {
            binding.model = item
        }
    }

}