package com.nemov.android.hellopepper.greeting.goodbye

import com.nemov.android.hellopepper.logger.LoggerGatewayImpl
import kotlinx.coroutines.Dispatchers

abstract class BaseGoodbyeServiceLocator : GoodbyeServiceLocator {
    override fun provideUseCaseDispatcher() = Dispatchers.Unconfined

    override fun provideGoodbyeUseCase() = GoodbyeUseCaseImpl(this)

    override fun provideGoodbyePresenter() = GoodbyePresenterImpl(this, provideGoodbyeView())

    override fun provideLoggerGateway() = LoggerGatewayImpl()

    override fun provideDispatcher() = Dispatchers.IO

    override fun provideGoodbyeController() = GoodbyeControllerImpl(provideQiContext(), provideDispatcher())
}