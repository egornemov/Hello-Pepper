package com.nemov.android.hellopepper.greeting.welcomming

class WelcommingUseCaseImpl(serviceProvider: WelcommingServiceProvider) : WelcommingUseCase {
    private val controller: WelcommingController by lazy { serviceProvider.provideWelcommingController() }

    override suspend fun startWelcommingChat() {
        controller.startWelcommingChat()
    }
}