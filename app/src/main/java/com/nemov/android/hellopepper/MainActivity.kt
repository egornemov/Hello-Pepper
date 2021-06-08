package com.nemov.android.hellopepper

import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.*
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.nemov.android.hellopepper.databinding.ActivityMainBinding
import com.nemov.android.hellopepper.greeting.goodbye.GoodbyePresenter
import com.nemov.android.libuniquejokes.JokePresenter
import kotlinx.coroutines.*

class MainActivity : RobotActivity(), RobotLifecycleCallbacks, GoodbyePresenter.GreetingView, JokePresenter.JokeView {

    private val mainScope = MainScope()

    private lateinit var binding: ActivityMainBinding

    private lateinit var goodbyePresenter: GoodbyePresenter

    private lateinit var jokePresenter: JokePresenter

    private fun initPresents() {
        val presentersProvider = application as PresentersProvider
        goodbyePresenter = presentersProvider.provideGreetingPresenter()
        jokePresenter = presentersProvider.provideJokePresenter()
    }

    private fun setViewContext() {
        val viewContext = application as ViewContext
        viewContext.setGreetingView(this)
        viewContext.setJokeView(this)
    }

    private fun setQiContext(qiContext: QiContext) {
        val qiContextProvider = application as QiContextProvider
        qiContextProvider.setQiContext(qiContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setViewContext()
        initPresents()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupDataFlow()
    }

    override fun onDestroy() {
        mainScope.cancel()
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    private lateinit var welcommingChat: Chat

    private fun initWelcommingChat(qiContext: QiContext) {
        val welcommingTopic = TopicBuilder.with(qiContext)
            .withResource(R.raw.welcomming)
            .build()
        val welcommingChatbot = QiChatbotBuilder.with(qiContext)
            .withTopic(welcommingTopic)
            .build()
        welcommingChat = ChatBuilder.with(qiContext)
            .withChatbot(welcommingChatbot)
            .build()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        setQiContext(qiContext)
        welcommingChat.run()
    }

    override fun onRobotFocusLost() {
        // The robot focus is lost.
    }

    override fun onRobotFocusRefused(reason: String) {
        // The robot focus is refused.
    }

    private fun setupDataFlow() {
        binding.bSayGoodbye.setOnClickListener {
            mainScope.launch {
                goodbyePresenter.startGoodbyeAction()
            }
        }
        binding.bMakeJoke.setOnClickListener {
            mainScope.launch {
                jokePresenter.makeJoke()
            }
        }
    }

    override fun goodbyeInAction() {
        binding.bSayGoodbye.isEnabled = false
    }

    override fun readyForGoodbye() {
        binding.bSayGoodbye.isEnabled = true
    }

    override fun showJoke(joke: String) {
        binding.etJokeHolder.setText(joke)
        binding.bMakeJoke.isEnabled = true
        binding.etJokeHolder.isEnabled = true
    }

    override fun loadingForJoke() {
        binding.bMakeJoke.isEnabled = false
        binding.etJokeHolder.isEnabled = false
    }

    override fun readyForJoke() {
        binding.bMakeJoke.isEnabled = true
        binding.etJokeHolder.isEnabled = true
    }
}