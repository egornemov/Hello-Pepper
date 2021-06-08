package com.nemov.android.hellopepper

import android.app.Application
import com.nemov.android.hellopepper.greeting.*
import com.nemov.android.libuniquejokes.BaseJokeServiceProvider
import com.nemov.android.libuniquejokes.JokePresenter

class HelloPepperApplication : Application(), PresentersProvider, ViewContext {

    private val greetingServiceProvider = object : BaseGreetingServiceProvider() {
        override fun provideGreetingView() = greetingView
    }

    private lateinit var greetingView: GreetingPresenter.GreetingView

    override fun setGreetingView(view: GreetingPresenter.GreetingView) {
        greetingView = view
    }

    private val jokeServiceProvider = object : BaseJokeServiceProvider() {
        override fun provideJokeView() = jokeView

        override fun provideContext() = this@HelloPepperApplication
    }

    private lateinit var jokeView: JokePresenter.JokeView

    override fun setJokeView(view: JokePresenter.JokeView) {
        jokeView = view
    }

    override fun provideGreetingPresenter() = greetingServiceProvider.provideGreetingPresenter()

    override fun provideJokePresenter() = jokeServiceProvider.provideJokePresenter()
}

interface PresentersProvider {
    fun provideGreetingPresenter(): GreetingPresenter
    fun provideJokePresenter(): JokePresenter
}

interface ViewContext {
    fun setGreetingView(view: GreetingPresenter.GreetingView)
    fun setJokeView(view: JokePresenter.JokeView)
}