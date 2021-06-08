package com.nemov.android.hellopepper.greeting.welcomming

class WelcommingPresenterImpl(serviceProvider: WelcommingServiceProvider) : WelcommingPresenter {
    private val useCase = serviceProvider.provideWelcommingUseCase()

    override suspend fun startWelcommingChat() {
        useCase.startWelcommingChat()
    }
}