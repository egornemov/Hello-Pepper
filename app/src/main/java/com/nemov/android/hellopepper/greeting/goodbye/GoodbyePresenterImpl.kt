package com.nemov.android.hellopepper.greeting.goodbye

class GoodbyePresenterImpl(serviceProvider: GoodbyeServiceProvider,
                           private val view: GoodbyePresenter.GreetingView
) : GoodbyePresenter {

    private val useCase = serviceProvider.provideGoodbyeUseCase()

    override suspend fun startGoodbyeAction() {
        view.goodbyeInAction()
        useCase.doGoodbyeAction()
    }

    override fun finishGoodbyeAction() {
        view.readyForGoodbye()
    }
}