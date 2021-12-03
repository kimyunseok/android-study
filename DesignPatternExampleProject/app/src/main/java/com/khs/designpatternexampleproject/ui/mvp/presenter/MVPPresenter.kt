package com.khs.designpatternexampleproject.ui.mvp.presenter

import com.khs.designpatternexampleproject.R
import com.khs.designpatternexampleproject.model.UserModel
import com.khs.designpatternexampleproject.ui.mvp.contract.MVPContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MVPPresenter(_userModel: UserModel, _mvpView: MVPContract.View): MVPContract.Presenter {
    private val userModel = _userModel
    private val mvpView = _mvpView

    override fun initUserInfo() {
        val user = userModel.getUserInfo()
        user?.let { // 받아온 데이터가 NULL이 아닌 경우만 View에 처리해줌
            mvpView.getUserInfo(user)
            mvpView.setInputText(user)
        }
    }

    override fun updateUserInfoModel() {
        mvpView.getCurrentInputUserInfo()?.let {
            mvpView.showLoadingDialog()

            userModel.modifyUserInfo(
                it.userEmail,
                it.userName,
                it.userContact,
                it.userAddress,
                it.userAge
            )
            mvpView.setInputText(it)
            mvpView.showToastMessage(mvpView.mContext?.getString(R.string.edit_complete_msg).toString())

            CoroutineScope(Dispatchers.Main).launch {
                delay(3000L)
                mvpView.hideLoadingDialog()
                mvpView.getUserInfo(it)
            }
        }
    }
}