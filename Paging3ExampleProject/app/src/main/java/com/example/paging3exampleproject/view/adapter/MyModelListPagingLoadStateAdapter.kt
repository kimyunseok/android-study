package com.example.paging3exampleproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3exampleproject.databinding.ViewMyModelLoadStateBinding

class MyModelListPagingLoadStateAdapter(private val retryCallback: () -> Unit):
    LoadStateAdapter<MyModelListPagingLoadStateAdapter.MyModelLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MyModelLoadStateViewHolder {
        val binding = ViewMyModelLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyModelLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyModelLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class MyModelLoadStateViewHolder(private val viewMyModelLoadStateBinding: ViewMyModelLoadStateBinding):
        RecyclerView.ViewHolder(viewMyModelLoadStateBinding.root) {
        fun bind(loadState: LoadState) {
            viewMyModelLoadStateBinding.retryBtn.setOnClickListener { retryCallback() }

            viewMyModelLoadStateBinding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                errorLayout.isVisible = loadState is LoadState.Error
            }
        }
    }

}