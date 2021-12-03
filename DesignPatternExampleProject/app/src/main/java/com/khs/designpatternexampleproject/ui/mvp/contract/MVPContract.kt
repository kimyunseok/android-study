package com.khs.designpatternexampleproject.ui.mvp.contract

import android.content.Context
import com.khs.designpatternexampleproject.model.User

/**
 * Contract는 MVP의 Interface입니다.
 * Model, View, Presenter에서 사용하게 될 기능들을 여기에 구현해야 합니다.
 *
 * View에는 setText()같은 View 처리 관련 메서드들이,
 * Presenter에는 Business Logic( View -> Presenter -> Model / Model -> Presenter -> View)같은
 * 메서드들이 존재해야 합니다.
 *
 */
interface MVPContract {
    interface View {
        val mContext: Context?

        fun setMVPTitle()

        fun setUpBtnListener()

        fun setInputText(user: User?)

        fun getUserInfo(user: User?)

        fun getCurrentInputUserInfo(): User?

        fun showLoadingDialog()
        fun hideLoadingDialog()

        fun showToastMessage(msg: String)
    }

    interface Presenter {
        fun initUserInfo()

        fun updateUserInfoModel()
    }
}