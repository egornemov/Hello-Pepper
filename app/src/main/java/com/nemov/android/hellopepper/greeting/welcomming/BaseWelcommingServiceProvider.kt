package com.nemov.android.hellopepper.greeting.welcomming

import kotlinx.coroutines.Dispatchers

abstract class BaseWelcommingServiceProvider : WelcommingServiceProvider {
    override fun provideWelcommingUseCase() = WelcommingUseCaseImpl(this)

    override fun provideWelcommingPresenter() = WelcommingPresenterImpl(this)

    override fun provideWelcommingController() = WelcommingControllerImpl(provideQiContext(), provideDispatcher())

    override fun provideDispatcher() = Dispatchers.IO
}