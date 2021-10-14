package com.khs.recyclerviewrefreshandinfinityscrollexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.recyclerviewrefreshandinfinityscrollexample.adapter.MyAdapter
import com.khs.recyclerviewrefreshandinfinityscrollexample.databinding.ActivityMainBinding
import com.khs.recyclerviewrefreshandinfinityscrollexample.model.RecyclerModel
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var totalItemList: MutableList<RecyclerModel> // 모든 item을 가지고 있는 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        setUpRecyclerView()
        setUpSwipeRefresh()
    }

    // 리스트 초기화 메서드
    private fun initList() {
        totalItemList = mutableListOf(
            RecyclerModel("🌭", "핫도그"),
            RecyclerModel("🍔", "햄버거"),
            RecyclerModel("🍟", "감자튀김"),
            RecyclerModel( "🍕", "피자"),
            RecyclerModel("🍜", "라면"),
            RecyclerModel( "🍩", "도넛"),
            RecyclerModel( "🍗", "치킨")
        )
    }

    //리사이클러뷰 셋팅 메서드
    private fun setUpRecyclerView() {
        val infinityItemList = mutableListOf<RecyclerModel>() // 무한 스크롤 리사이클러뷰 아이템리스트
        setListRandom(infinityItemList)
        binding.infinityRecyclerView.adapter = MyAdapter(applicationContext, infinityItemList)
        binding.infinityRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val refreshItemList = mutableListOf<RecyclerModel>() // 새로고침 리사이클러뷰 아이템 리스트
        setListRandom(refreshItemList)
        binding.refreshRecyclerView.adapter = MyAdapter(applicationContext, refreshItemList)
        binding.refreshRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    // 스와이프 이벤트 생성 메서드
    private fun setUpSwipeRefresh() {
        //새로고침 리사이클러뷰의 어댑터를 통해 불러온 List와 원래 refreshItemList이 다른 주소를 가지고 있었음.
        binding.swipeLayout.setOnRefreshListener {
            val list = (binding.refreshRecyclerView.adapter as MyAdapter).itemList
            setListRandom(list)
            binding.swipeLayout.isRefreshing = false
            binding.refreshRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    // 새로고침 메서드
    private fun setListRandom(list: MutableList<RecyclerModel>) {
        list.clear()
        for(idx in 0..6) {
            val randomIdx = (Math.random() * 7).toInt()
            list.add(totalItemList[randomIdx])
        }
    }
}