package com.nemov.android.hellopepper.greeting

import com.nemov.android.hellopepper.logger.LoggerGateway

interface GreetingServiceProvider {
    fun provideGreetingUseCase(): GreetingUseCase
    fun provideGreetingView(): GreetingPresenter.GreetingView
    fun provideGreetingPresenter(): GreetingPresenter
    fun provideLoggerGateway(): LoggerGateway
}