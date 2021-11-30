package com.khs.roomdbexampleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.roomdbexampleproject.adapter.MemoRecyclerViewAdapter
import com.khs.roomdbexampleproject.data.AppDataBase
import com.khs.roomdbexampleproject.data.ViewModelFactory
import com.khs.roomdbexampleproject.data.model.Memo
import com.khs.roomdbexampleproject.data.repository.MemoRepository
import com.khs.roomdbexampleproject.data.viewmodel.memo.MemoViewModel
import com.khs.roomdbexampleproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var viewModelFactory: ViewModelFactory
    lateinit var memoViewModel: MemoViewModel

    lateinit var memoList: MutableList<Memo>
    lateinit var memoRecyclerViewAdapter: MemoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initActivity()
    }

    private fun initActivity() {
        initViewModel()
        setUpObserver()
        getAllMemo()
        setUpBtnListener()
    }

    private fun initViewModel() {
        viewModelFactory = ViewModelFactory(MemoRepository())
        memoViewModel = ViewModelProvider(this, viewModelFactory).get(MemoViewModel::class.java)
    }

    private fun setUpObserver() {
        memoViewModel.isGetAllMemoComplete.observe(this) {
            memoList = it.toMutableList()
            Log.d("getList::", "size is " + it.size.toString())
            setUpRecyclerView()
        }

        memoViewModel.isMemodeleteComplete.observe(this) {
            Log.d("deleteComplete::", "memo delete")

            val position = memoList.indexOf(it)
            memoList.removeAt(position)
            memoRecyclerViewAdapter.notifyItemRemoved(position)
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }

        memoViewModel.isMemodeleteByIdComplete.observe(this) {
            Log.d("deleteComplete::", "memo delete")

            val position = memoList.indexOf(it)
            memoList.removeAt(position)
            memoRecyclerViewAdapter.notifyItemRemoved(position)
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }

        memoViewModel.isMemoInsertComplete.observe(this) {
            id ->
            Log.d("insertComplete::", "memo id is $id")
            memoList.add(Memo(id, binding.input.toString(), false))
            binding.input = ""
            memoRecyclerViewAdapter.notifyItemInserted(memoList.size - 1)
        }

        memoViewModel.isEdit.observe(this) {
            binding.isEditing = it.isEdit
            val position = memoList.indexOf(it.memo)

            if(it.isEdit) {
                if(memoRecyclerViewAdapter.lastEditIdx != -1) {
                    memoList[memoRecyclerViewAdapter.lastEditIdx].editMode = false
                    memoRecyclerViewAdapter.notifyItemChanged(memoRecyclerViewAdapter.lastEditIdx)
                }

                memoRecyclerViewAdapter.lastEditIdx = position
                memoList[position].editMode = true
                memoRecyclerViewAdapter.notifyItemChanged(position)
            }

        }

        memoViewModel.isMemoModifyComplete.observe(this) {
            Log.d("modifyComplete::", "memo modified")

            val position = memoList.indexOf(it.memo)

            memoList[position].memo = it.editMemo
            memoList[position].editMode = false

            memoRecyclerViewAdapter.lastEditIdx = -1
            memoRecyclerViewAdapter.notifyItemChanged(position)
        }
    }

    private fun getAllMemo() {
        memoViewModel.getAllMemo()
    }

    private fun insertMemo() {
        if(binding.input.toString().trim().isEmpty().not()) {
            val memo = Memo(0, binding.input.toString(), false)
            memoViewModel.insertMemo(memo)
        }
    }

    private fun setUpRecyclerView() {
        memoRecyclerViewAdapter = MemoRecyclerViewAdapter(baseContext, memoList, memoViewModel)
        binding.mainRecyclerView.adapter = memoRecyclerViewAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(baseContext)
    }

    private fun setUpBtnListener() {
        binding.inputBtn.setOnClickListener { insertMemo() }
    }
}