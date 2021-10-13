package com.khs.doublerecyclerviewusingdatabindingexampleproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.doublerecyclerviewusingdatabindingexampleproject.adapter.OutRecyclerViewAdapter
import com.khs.doublerecyclerviewusingdatabindingexampleproject.databinding.FragmentMainBinding
import com.khs.doublerecyclerviewusingdatabindingexampleproject.model.RecyclerInViewModel
import com.khs.doublerecyclerviewusingdatabindingexampleproject.model.RecyclerOutViewModel

class MainFragment: Fragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        var itemList = mutableListOf(
            RecyclerOutViewModel("포유류", mutableListOf(
                RecyclerInViewModel("🐶", "강아지"), RecyclerInViewModel("🐱", "고양이"),
                RecyclerInViewModel("🐳", "고래"), RecyclerInViewModel("🦒", "사슴")
            )
            ),

            RecyclerOutViewModel("조류", mutableListOf(
                RecyclerInViewModel("🦅", "독수리"), RecyclerInViewModel("🕊️", "비둘기"),
                RecyclerInViewModel("🦉", "부엉이"), RecyclerInViewModel("🐔", "닭")
            )
            ),

            RecyclerOutViewModel("어류", mutableListOf(
                RecyclerInViewModel("🐟", "홍어"), RecyclerInViewModel("🐟", "광어"),
                RecyclerInViewModel("🐟", "연어"), RecyclerInViewModel("🐟", "우럭")
            )
            ),

            RecyclerOutViewModel("파충류", mutableListOf(
                RecyclerInViewModel("🐊", "악어"), RecyclerInViewModel("🦎", "카멜레온"),
                RecyclerInViewModel("🦎", "도마뱀"), RecyclerInViewModel("🐍", "뱀")
            )
            ),

            RecyclerOutViewModel("양서류", mutableListOf(
                RecyclerInViewModel("🐸", "개구리"), RecyclerInViewModel("🦎", "도룡뇽"),
                RecyclerInViewModel("🐸", "두꺼비")
            )
            ),
        )

        binding.outRecyclerview.adapter = OutRecyclerViewAdapter(requireContext(), itemList)
        binding.outRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}