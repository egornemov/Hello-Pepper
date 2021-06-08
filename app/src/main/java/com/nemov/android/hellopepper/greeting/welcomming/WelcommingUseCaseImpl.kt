package com.nemov.android.hellopepper.greeting.welcomming

class WelcommingUseCaseImpl(serviceLocator: WelcommingServiceLocator) : WelcommingUseCase {
    private val controller: WelcommingController by lazy { serviceLocator.provideWelcommingController() }

    override suspend fun startWelcommingChat() {
        controller.startWelcommingChat()
    }
}