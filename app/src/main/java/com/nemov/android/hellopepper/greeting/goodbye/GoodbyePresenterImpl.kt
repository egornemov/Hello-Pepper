package com.nemov.android.hellopepper.greeting.goodbye

class GoodbyePresenterImpl(serviceLocator: GoodbyeServiceLocator,
                           private val view: GoodbyePresenter.GreetingView
) : GoodbyePresenter {

    private val useCase = serviceLocator.provideGoodbyeUseCase()

    override suspend fun startGoodbyeAction() {
        view.goodbyeInAction()
        useCase.doGoodbyeAction()
    }

    override fun finishGoodbyeAction() {
        view.readyForGoodbye()
    }
}