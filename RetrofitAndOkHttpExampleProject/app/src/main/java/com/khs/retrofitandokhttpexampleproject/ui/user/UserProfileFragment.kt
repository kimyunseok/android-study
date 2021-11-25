package com.khs.retrofitandokhttpexampleproject.ui.user

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.khs.retrofitandokhttpexampleproject.R
import com.khs.retrofitandokhttpexampleproject.api.APIViewModelFactory
import com.khs.retrofitandokhttpexampleproject.api.SolvedAcAPI
import com.khs.retrofitandokhttpexampleproject.api.SolvedAcAPIRepository
import com.khs.retrofitandokhttpexampleproject.api.apiviewmodel.UserDataViewModel
import com.khs.retrofitandokhttpexampleproject.common.GlobalApplication.Companion.baseService
import com.khs.retrofitandokhttpexampleproject.databinding.FragmentUserProfileBinding
import com.khs.retrofitandokhttpexampleproject.model.SolveAcGetUserDataModel
import com.khs.retrofitandokhttpexampleproject.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileFragment: BaseFragment<FragmentUserProfileBinding>() {
    override val layoutId: Int = R.layout.fragment_user_profile

    lateinit var viewModelFactory: APIViewModelFactory
    lateinit var userDataViewModel: UserDataViewModel

    override fun init() {
        initViewModel()
        setUpObserver()
        getUserData()

//        val handle = arguments?.getString("handle")
//        getUserDataByCall(handle.toString())
    }

    private fun initViewModel() {
        viewModelFactory = APIViewModelFactory(SolvedAcAPIRepository())
        userDataViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(UserDataViewModel::class.java)
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

    fun getUserDataByCall(handle: String) {
        val apiClient = baseService.create(SolvedAcAPI::class.java)

        apiClient.getUserDataByCall(handle).enqueue(object: Callback<SolveAcGetUserDataModel> {
            override fun onResponse(
                call: Call<SolveAcGetUserDataModel>,
                response: Response<SolveAcGetUserDataModel>
            ) {
                Log.d("api_request_url::", response.raw().request.url.toString())
                Log.d("get_user_api", response.code().toString() + " " + response.message())
                if(response.isSuccessful) {
                    viewDataBinding.model = response.body()?.apply { code = response.code() }
                } else {
                    viewDataBinding.model = SolveAcGetUserDataModel(
                        "",
                        "",
                        mutableListOf(),
                        SolveAcGetUserDataModel.BackgroundData("", "", ""),
                        "",
                        0,
                        0
                    ).apply { code = response.code() }
                    // 서버와 통신 에러, 헤더 문제, 서버 내부 문제 등
                }
            }

            override fun onFailure(call: Call<SolveAcGetUserDataModel>, t: Throwable) {
                //대부분의 경우에 Client의 문제.
            }

        })
    }

}