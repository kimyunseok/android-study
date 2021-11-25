package com.khs.retrofitandokhttpexampleproject.ui.user

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.khs.retrofitandokhttpexampleproject.R
import com.khs.retrofitandokhttpexampleproject.api.APIViewModelFactory
import com.khs.retrofitandokhttpexampleproject.api.SolvedAcAPIRepository
import com.khs.retrofitandokhttpexampleproject.api.apiviewmodel.UserDataViewModel
import com.khs.retrofitandokhttpexampleproject.databinding.FragmentUserProfileBinding
import com.khs.retrofitandokhttpexampleproject.ui.BaseFragment

class UserProfileFragment: BaseFragment<FragmentUserProfileBinding>() {
    override val layoutId: Int = R.layout.fragment_user_profile

    lateinit var viewModelFactory: APIViewModelFactory
    lateinit var userDataViewModel: UserDataViewModel

    override fun init() {
        initViewModel()
        setUpObserver()
        getUserData()
    }

    private fun initViewModel() {
        viewModelFactory = APIViewModelFactory(SolvedAcAPIRepository())
        userDataViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(UserDataViewModel::class.java)
    }

    private fun setUpObserver() {
        userDataViewModel.getUserDataRepositories.observe(viewLifecycleOwner) {
            data ->
            viewDataBinding.model = data

            if(data.code != 200) {
                Toast.makeText(requireContext(), context?.getString(R.string.no_user_id), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserData() {
        val handle = arguments?.getString("handle")
        userDataViewModel.getUserData(handle.toString())
    }

}