package com.khs.roomdbexampleproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khs.roomdbexampleproject.data.AppDataBase
import com.khs.roomdbexampleproject.data.model.Memo
import com.khs.roomdbexampleproject.data.viewmodel.memo.MemoViewModel
import com.khs.roomdbexampleproject.databinding.HolderMemoBinding
import com.khs.roomdbexampleproject.ui.MainActivity
import com.khs.roomdbexampleproject.util.MemoDiffUtil

class MemoRecyclerViewAdapter(val context: Context, val itemList: MutableList<Memo>,
                              val memoViewModel: MemoViewModel): RecyclerView.Adapter<MemoRecyclerViewAdapter.Holder>() {

    private var lastEditIdx: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HolderMemoBinding.inflate(LayoutInflater.from(context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(val binding: HolderMemoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Memo, position: Int) {
            binding.memo = item
            binding.input = item.memo
            //input을 따로 만든 이유 : 양방향 데이터 바인딩을 사용할 때
            //memo 모델의 memo 변수를 쓰면 리사이클러 뷰 내에서 자동으로 업데이트 된다.

            binding.editBtn.setOnClickListener {
                memoViewModel.changeMode(true)
                if(lastEditIdx != -1) {
                    itemList[lastEditIdx].editMode = false
                    notifyItemChanged(lastEditIdx)
                }

                lastEditIdx = position
                item.editMode = true
                notifyItemChanged(position)
            }

            binding.removeBtn.setOnClickListener {
                memoViewModel.deleteMemoById(item.id)
                itemList.remove(item)
                notifyItemRemoved(position)
                notifyItemChanged(position)
            }
            binding.completeBtn.setOnClickListener {
                memoViewModel.changeMode(false)
                memoViewModel.modifyMemo(item.id, binding.input.toString())

                item.memo = binding.input.toString()
                item.editMode = false

                notifyItemChanged(position)
            }
        }
    }

}