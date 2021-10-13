package com.khs.doublerecyclerviewusingdatabindingexampleproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khs.doublerecyclerviewusingdatabindingexampleproject.databinding.HolderRecyclerviewOutBinding
import com.khs.doublerecyclerviewusingdatabindingexampleproject.model.RecyclerOutViewModel

class OutRecyclerViewAdapter(val context: Context, val itemList: MutableList<RecyclerOutViewModel>): RecyclerView.Adapter<OutRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HolderRecyclerviewOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: HolderRecyclerviewOutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecyclerOutViewModel) {
            binding.model = item

            binding.innerRecyclerview.adapter = InRecyclerViewAdapter(context, item.innerList)
            binding.innerRecyclerview.layoutManager = LinearLayoutManager(context)
        }
    }

}