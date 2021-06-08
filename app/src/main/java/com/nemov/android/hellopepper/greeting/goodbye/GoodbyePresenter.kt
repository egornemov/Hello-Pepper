package com.nemov.android.hellopepper.greeting.goodbye

interface GoodbyePresenter {
    suspend fun startGoodbyeAction()
    fun finishGoodbyeAction()

    interface GreetingView {
        fun goodbyeInAction()
        fun readyForGoodbye()
    }
}