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
import com.nemov.android.hellopepper.greeting.GreetingPresenter
import com.nemov.android.libuniquejokes.JokePresenter
import kotlinx.coroutines.*

class MainActivity : RobotActivity(), RobotLifecycleCallbacks, GreetingPresenter.GreetingView, JokePresenter.JokeView {

    private val mainScope = MainScope()

    private lateinit var binding: ActivityMainBinding

    private lateinit var greetingPresenter: GreetingPresenter

    private lateinit var jokePresenter: JokePresenter

    private fun initPresents() {
        val presentersProvider = application as PresentersProvider
        greetingPresenter = presentersProvider.provideGreetingPresenter()
        jokePresenter = presentersProvider.provideJokePresenter()
    }

    private fun setViewContext() {
        val viewContext = application as ViewContext
        viewContext.setGreetingView(this)
        viewContext.setJokeView(this)
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

    private lateinit var goodbyeChat: Chat

    private fun initGoodbyeChat(qiContext: QiContext) {
        val goodbyeTopic = TopicBuilder.with(qiContext)
            .withResource(R.raw.goodbye)
            .build()
        val goodbyeChatbot = QiChatbotBuilder.with(qiContext)
            .withTopic(goodbyeTopic)
            .build()
        goodbyeChat = ChatBuilder.with(qiContext)
            .withChatbot(goodbyeChatbot)
            .build()
        val bookmarks: Map<String, Bookmark> = goodbyeTopic.bookmarks
        val proposalBookmark = bookmarks["goodbye_proposal"]
        goodbyeChat.addOnStartedListener {
            goodbyeChatbot.goToBookmark(proposalBookmark, AutonomousReactionImportance.HIGH, AutonomousReactionValidity.IMMEDIATE)
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        initGoodbyeChat(qiContext)
        initWelcommingChat(qiContext)
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
                greetingPresenter.makeGreeting()
            }
        }
        binding.bMakeJoke.setOnClickListener {
            mainScope.launch {
                jokePresenter.makeJoke()
            }
        }
    }

    override fun startGreeting(message: String) {
        mainScope.launch {
            goodbyeChat.run()
            greetingPresenter.finishGreeting()
        }
    }

    override fun loadingForGreeting() {
        binding.bSayGoodbye.isEnabled = false
    }

    override fun readyForGreeting() {
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