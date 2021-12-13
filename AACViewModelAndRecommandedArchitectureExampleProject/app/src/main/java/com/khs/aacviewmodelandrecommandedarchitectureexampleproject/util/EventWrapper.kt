package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.util

/**
 * EventWrapper
 * https://github.com/nirmaljeffrey/SingleLiveEvent-EventWrapper-LiveData
 * 옵저버가 Detach -> Attach 됐을 때 Observe하는 경우를 방지하기 위해 만들었다.
 *
 * SingleLiveEvent도 있지만 여러 개의 Observer 사용이 불가능해서 EventWrapper가 나은 판단이라 생각했다.
 *
 */
class EventWrapper<T>(content: T) {
    private val mContent: T // 현재 들어온 값
    private var hasBeenObserved = false  // 예전에 다루어진 Content인가?

    init {
        requireNotNull("NULL Values in EveneWrapper are Not Allowed.")
        mContent = content
    }

    //이전에 Observe한 값은 처리하지 않는 변수
    val contentIfNotHandled: T?
        get() = if(hasBeenObserved) {
            null
        } else {
            hasBeenObserved = true
            mContent
        }

    // Observe 여부 상관없이 가장 최신 Data return.
    fun peekContent(): T {
        return mContent
    }

    // 이전에 처리된 값인지 Return.
    fun hasBeenObserved(): Boolean {
        return hasBeenObserved
    }
}