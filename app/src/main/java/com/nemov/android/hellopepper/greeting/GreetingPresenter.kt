package com.nemov.android.hellopepper.greeting

interface GreetingPresenter {
    suspend fun makeGreeting()
    fun startGreeting(message: String)
    fun finishGreeting()

    interface GreetingView {
        fun startGreeting(message: String)
        fun loadingForGreeting()
        fun readyForGreeting()
    }
}