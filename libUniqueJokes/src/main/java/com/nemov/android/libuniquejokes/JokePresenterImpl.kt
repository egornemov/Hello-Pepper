package com.nemov.android.libuniquejokes

class JokePresenterImpl(serviceLocator: JokeServiceLocator, private val view: JokePresenter.JokeView)
    : JokePresenter {

    private val useCase = serviceLocator.provideJokeUseCase()

    override suspend fun makeJoke() {
        view.loadingForJoke()
        useCase.makeJoke()
    }

    override fun showJoke(joke: String) {
        view.showJoke(joke)
        view.readyForJoke()
    }

    override fun showError() {
        view.showJoke(JOKE_ERROR)
        view.readyForJoke()
    }

    companion object {
        const val JOKE_ERROR = "Not a joke time!"
    }
}