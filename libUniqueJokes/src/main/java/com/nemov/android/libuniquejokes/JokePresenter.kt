package com.nemov.android.libuniquejokes

interface JokePresenter {
    suspend fun makeJoke()
    fun showJoke(joke: String)
    fun showError()

    interface JokeView {
        fun showJoke(joke: String)
        fun loadingForJoke()
        fun readyForJoke()
    }
}