package com.nemov.android.hellopepper

import android.app.Application
import com.aldebaran.qi.sdk.QiContext
import com.nemov.android.hellopepper.greeting.*
import com.nemov.android.hellopepper.greeting.goodbye.BaseGoodbyeServiceLocator
import com.nemov.android.hellopepper.greeting.goodbye.GoodbyePresenter
import com.nemov.android.hellopepper.greeting.welcomming.BaseWelcommingServiceLocator
import com.nemov.android.hellopepper.greeting.welcomming.WelcommingPresenter
import com.nemov.android.libuniquejokes.BaseJokeServiceLocator
import com.nemov.android.libuniquejokes.JokePresenter

class HelloPepperApplication : Application(), PresentersProvider, ViewContext, QiContextProvider {

    private val welcommingServiceLocator = object : BaseWelcommingServiceLocator() {
        override fun provideQiContext() = qiContext
    }

    private val goodbyeServiceLocator = object : BaseGoodbyeServiceLocator() {
        override fun provideGoodbyeView() = goodbyeView
        override fun provideQiContext() = qiContext
    }

    private lateinit var goodbyeView: GoodbyePresenter.GreetingView

    override fun setGreetingView(view: GoodbyePresenter.GreetingView) {
        goodbyeView = view
    }

    private val jokeServiceLocator = object : BaseJokeServiceLocator() {
        override fun provideJokeView() = jokeView
        override fun provideContext() = this@HelloPepperApplication
    }

    private lateinit var jokeView: JokePresenter.JokeView

    override fun setJokeView(view: JokePresenter.JokeView) {
        jokeView = view
    }

    override fun provideWelcommigPresenter() = welcommingServiceLocator.provideWelcommingPresenter()

    override fun provideGoodbyePresenter() = goodbyeServiceLocator.provideGoodbyePresenter()

    override fun provideJokePresenter() = jokeServiceLocator.provideJokePresenter()

    private lateinit var qiContext: QiContext

    override fun setQiContext(qiContext: QiContext) {
        this.qiContext = qiContext
    }
}

interface PresentersProvider {
    fun provideWelcommigPresenter(): WelcommingPresenter
    fun provideGoodbyePresenter(): GoodbyePresenter
    fun provideJokePresenter(): JokePresenter
}

interface ViewContext {
    fun setGreetingView(view: GoodbyePresenter.GreetingView)
    fun setJokeView(view: JokePresenter.JokeView)
}

interface  QiContextProvider {
    fun setQiContext(qiContext: QiContext)
}