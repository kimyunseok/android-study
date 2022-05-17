package com.example.paging3exampleproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3exampleproject.data.MyModel
import com.example.paging3exampleproject.databinding.ViewMyModelHolderBinding

class MyModelListPagingAdapter: PagingDataAdapter<MyModel, MyModelListPagingAdapter.MyModelViewHolder>(
    object: DiffUtil.ItemCallback<MyModel>() {
        override fun areItemsTheSame(oldItem: MyModel, newItem: MyModel): Boolean {
            // 같은 객체인지 check
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MyModel, newItem: MyModel): Boolean {
            // 같은 내용물인지 check
            return oldItem.idx == newItem.idx
        }
    }
) {

    val contentsType = 1
    val loadStateType = 2

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            contentsType
        } else {
            loadStateType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyModelViewHolder {
        val viewBinding = ViewMyModelHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyModelViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: MyModelViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    inner class MyModelViewHolder(private val viewMyModelHolderBinding: ViewMyModelHolderBinding): RecyclerView.ViewHolder(viewMyModelHolderBinding.root) {
        fun bind(item: MyModel) {
            viewMyModelHolderBinding.myModel = item
        }
    }

}