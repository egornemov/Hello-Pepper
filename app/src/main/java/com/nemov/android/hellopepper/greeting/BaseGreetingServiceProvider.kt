package com.nemov.android.hellopepper.greeting

import com.nemov.android.hellopepper.logger.LoggerGatewayImpl

abstract class BaseGreetingServiceProvider : GreetingServiceProvider {
    override fun provideGreetingUseCase() = GreetingUseCaseImpl(this)

    override fun provideGreetingPresenter() = GreetingPresenterImpl(this, provideGreetingView())

    override fun provideLoggerGateway() = LoggerGatewayImpl()
}