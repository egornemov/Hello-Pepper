package com.nemov.android.libuniquejokes

import kotlinx.coroutines.Dispatchers

abstract class BaseJokeServiceProvider: JokeServiceProvider {
    override fun provideJokeUseCase() = JokeUseCaseImpl(this)

    override fun provideJokePresenter() = JokePresenterImpl(this, provideJokeView())

    override fun provideJokeController() = JokeControllerImpl()

    override fun provideDispatcher() = Dispatchers.IO

    override fun provideJokeGateway() = JokeGatewayImpl(provideContext(), provideDispatcher())
}