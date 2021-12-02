package com.khs.designpatternexampleproject.ui.mvvm.viewmodel.eventwrapper

/**
 * EventWrapper
 * https://github.com/nirmaljeffrey/SingleLiveEvent-EventWrapper-LiveData
 * 옵저버가 Detach -> Attach 됐을 때 Observe하는 경우를 방지하기 위해 만들었다.
 *
 * SingleLiveEvent도 있지만 여러 개의 Observer 사용이 불가능해서 EventWrapper가 나은 판단이라 생각했다.
 *
 */
class Event<T>(content: T?) {
    private val mContent: T // 현재 들어온 값
    private var hasBeenHandled = false // 예전에 다루어진 Content인가?

    // 이전에 처리한 이벤트라면, 다시 처리하지 않는다.
    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) { // Observe된 적이 있다면
            null // null 리턴
        } else { // Observe된 적이 없다면
            hasBeenHandled = true // 됐다고 표기한 후에
            mContent //해당 값 return.
        }

    init {
        requireNotNull(content) { "null values in Event are not allowed." } // content는 null값 허용하지 않는다.
        mContent = content
    }

    // 이전에 처리한 이벤트도 다시 return해준다.
    fun peekContent(): T {
        return mContent
    }

    // 이전에 처리됐었는지를 Return.
    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

}