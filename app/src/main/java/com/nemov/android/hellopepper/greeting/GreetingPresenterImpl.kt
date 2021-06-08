package com.nemov.android.hellopepper.greeting

class GreetingPresenterImpl(serviceProvider: GreetingServiceProvider,
                            private val view: GreetingPresenter.GreetingView) : GreetingPresenter {

    private val useCase = serviceProvider.provideGreetingUseCase()

    override suspend fun makeGreeting() {
        view.loadingForGreeting()
        useCase.makeGreeting()
    }

    override fun startGreeting(message: String) {
        view.startGreeting(message)
    }

    override fun finishGreeting() {
        useCase.finishGreeting()
        view.readyForGreeting()
    }
}