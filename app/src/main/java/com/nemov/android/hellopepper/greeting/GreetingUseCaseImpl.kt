package com.nemov.android.hellopepper.greeting

import kotlinx.coroutines.delay

class GreetingUseCaseImpl(serviceProvider: GreetingServiceProvider): GreetingUseCase {

    private val presenter: GreetingPresenter by lazy { serviceProvider.provideGreetingPresenter() }
    private val loggerGateway = serviceProvider.provideLoggerGateway()

    override suspend fun makeGreeting() {
        val message = "Message"
        delay(5000)
        presenter.startGreeting(message)
    }

    override fun finishGreeting() {
        loggerGateway.log(TAG, "Greeting is finished")
    }

    companion object {
        private const val TAG = "Hello Pepper"
    }
}