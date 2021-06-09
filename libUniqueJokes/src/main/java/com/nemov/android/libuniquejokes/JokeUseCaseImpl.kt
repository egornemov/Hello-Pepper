package com.nemov.android.libuniquejokes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class JokeUseCaseImpl(serviceLocator: JokeServiceLocator,
                      private val numberOfAttempts: Int = NUMBER_OF_ATTEMPTS): JokeUseCase {

    private val dispatcher = serviceLocator.provideUseCaseDispatcher()
    private val presenter: JokePresenter by lazy { serviceLocator.provideJokePresenter() }
    private val controller = serviceLocator.provideJokeController()
    private val gateway = serviceLocator.provideJokeGateway()

    override suspend fun makeJoke() {
        CoroutineScope(dispatcher).launch {
            val joke = uniqueJoke()
            joke?.run {
                presenter.showJoke(joke)
                gateway.saveJoke(joke)
            } ?: run {
                presenter.showError()
            }
        }
    }

    private suspend fun uniqueJoke(attempt: Int = numberOfAttempts): String? {
        val jokes = gateway.getJokes()
        val joke = controller.getJoke()
        return if (attempt > 0 && jokes.contains(joke)) {
            uniqueJoke(attempt - 1)
        } else {
            joke
        }
    }

    companion object {
        const val NUMBER_OF_ATTEMPTS = 5
    }
}