package com.nemov.android.hellopepper.greeting.welcomming

import com.aldebaran.qi.sdk.QiContext
import kotlinx.coroutines.CoroutineDispatcher

interface WelcommingServiceLocator {
    fun provideWelcommingUseCase(): WelcommingUseCase
    fun provideWelcommingPresenter(): WelcommingPresenter
    fun provideWelcommingController(): WelcommingController
    fun provideQiContext(): QiContext
    fun provideDispatcher(): CoroutineDispatcher
}