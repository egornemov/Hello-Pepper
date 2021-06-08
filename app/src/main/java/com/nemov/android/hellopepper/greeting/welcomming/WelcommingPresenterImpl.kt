package com.nemov.android.hellopepper.greeting.welcomming

class WelcommingPresenterImpl(serviceLocator: WelcommingServiceLocator) : WelcommingPresenter {
    private val useCase = serviceLocator.provideWelcommingUseCase()

    override suspend fun startWelcommingChat() {
        useCase.startWelcommingChat()
    }
}