package com.nemov.android.libuniquejokes

import kotlinx.coroutines.Dispatchers

abstract class BaseJokeServiceLocator: JokeServiceLocator {
    override fun provideUseCaseDispatcher() = Dispatchers.Unconfined

    override fun provideJokeUseCase() = JokeUseCaseImpl(this)

    override fun provideJokePresenter() = JokePresenterImpl(this, provideJokeView())

    override fun provideJokeController() = JokeControllerImpl()

    override fun provideDispatcher() = Dispatchers.IO

    override fun provideJokeGateway() = JokeGatewayImpl(provideContext(), provideDispatcher())
}