package com.nemov.android.hellopepper.greeting.goodbye

import com.aldebaran.qi.sdk.QiContext
import com.nemov.android.hellopepper.logger.LoggerGateway
import kotlinx.coroutines.CoroutineDispatcher

interface GoodbyeServiceLocator {
    fun provideGoodbyeUseCase(): GoodbyeUseCase
    fun provideGoodbyeView(): GoodbyePresenter.GreetingView
    fun provideGoodbyePresenter(): GoodbyePresenter
    fun provideGoodbyeController(): GoodbyeController
    fun provideLoggerGateway(): LoggerGateway
    fun provideQiContext(): QiContext
    fun provideDispatcher(): CoroutineDispatcher
}