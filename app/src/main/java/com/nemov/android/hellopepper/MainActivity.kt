package com.nemov.android.hellopepper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nemov.android.hellopepper.databinding.ActivityMainBinding
import com.nemov.android.hellopepper.greeting.GreetingPresenter
import com.nemov.android.hellopepper.greeting.GreetingServiceProvider
import com.nemov.android.libuniquejokes.JokePresenter
import com.nemov.android.libuniquejokes.JokeServiceProvider
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), GreetingPresenter.GreetingView, JokePresenter.JokeView {

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
        setViewContext()
        initPresents()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupDataFlow()
    }

    override fun onDestroy() {
        mainScope.cancel()
        super.onDestroy()
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
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            delay(5000)
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
        binding.root.requestLayout()
        binding.root.invalidate()
    }
}