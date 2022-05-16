package com.example.paging3exampleproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paging3exampleproject.databinding.ActivityMainBinding
import com.example.paging3exampleproject.repo.MyRepository
import com.example.paging3exampleproject.view.adapter.MyModelListPagingAdapter
import com.example.paging3exampleproject.viewmodel.MainViewModel
import com.example.paging3exampleproject.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(MyRepository())).get(MainViewModel::class.java)
    }

    private val myModelListPagingAdapter: MyModelListPagingAdapter
        by lazy { MyModelListPagingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setUpObserver()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(baseContext, 3)
            adapter = myModelListPagingAdapter
        }
    }

    private fun setUpObserver() {
        mainViewModel.myModelPagingLiveData.observe(this) { pagingData ->
            lifecycleScope.launch {
                myModelListPagingAdapter.submitData(pagingData)
            }
        }
    }

}